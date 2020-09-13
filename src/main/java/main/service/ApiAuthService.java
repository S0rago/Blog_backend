package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import main.AppEnvironment;
import main.Response.AuthResponse;
import main.dto.users.AuthUser;
import main.model.CaptchaCode;
import main.model.User;
import main.repository.CaptchaCodeRepository;
import main.repository.PostRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import javax.mail.MessagingException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ApiAuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CaptchaCodeRepository captchaCodeRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private MailService mailService;

    @Autowired
    private AppEnvironment appEnv;



    public ApiAuthService(UserRepository userRepository, PostRepository postRepository, CaptchaCodeRepository captchaCodeRepository, Environment environment, MailService mailService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.captchaCodeRepository = captchaCodeRepository;
        this.environment = environment;
        this.mailService = mailService;
    }

    @Transactional
    public AuthResponse loginUser(String email, String password) {
        AuthResponse ar = new AuthResponse();
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);
        if (optionalUser.isEmpty()) {
            ar.setResult(false);
        } else {
            User user = optionalUser.get();
            AuthUser authUser = new AuthUser(user);
            if (authUser.getModeration()) {
                authUser.setSettings(true);
                authUser.setModerationCount(postRepository.countPostsToMod());
            }
            ar.setResult(true);
            ar.setUser(authUser);
            appEnv.addSession(RequestContextHolder.currentRequestAttributes().getSessionId(), authUser.getId());
        }
        return ar;
    }

    @Transactional
    public AuthResponse checkUser() {
        AuthResponse ar = new AuthResponse();
        Integer userId = appEnv.getUserIdFromSession(RequestContextHolder.currentRequestAttributes().getSessionId());
        if (userId == null) {
            ar.setResult(false);
        } else {
            User user = userRepository.findById(userId).get();
            AuthUser authUser = new AuthUser(user);
            if (authUser.getModeration()) {
                authUser.setSettings(true);
                authUser.setModerationCount(postRepository.countPostsToMod());
            }
            ar.setResult(true);
            ar.setUser(authUser);
            appEnv.addSession(RequestContextHolder.currentRequestAttributes().getSessionId(), authUser.getId());
        }
        return ar;
    }

    @Transactional
    public AuthResponse restorePass(String email) {
        AuthResponse ar = new AuthResponse();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            ar.setResult(false);
        } else {
            User user = optionalUser.get();
            String code = UUID.randomUUID().toString();
            user.setCode(code);
            userRepository.save(user);

            final String port = environment.getProperty("local.server.port");
            final String hostName = InetAddress.getLoopbackAddress().getHostName();
            final String url = String.format("http://%s:%s", hostName, port);

            try{
                mailService.send(user.getEmail(), "Ссылка на восстановление пароля",
                        String.format("Для восстановления пароля, пройдите по этой ссылке: " +
                                "%s/login/change-password/%s", url, code));
                ar.setResult(true);
            } catch (MessagingException ex) {
                ar.setResult(false);
            }
        }
        return ar;
    }

    @Transactional
    public AuthResponse changePass(String code, String password, String captcha, String captchaSecret) {
        AuthResponse ar = new AuthResponse();
        ar.setResult(false);
        Optional<User> user = userRepository.findByCode(code);
        if (user.isPresent()) {
            Optional<CaptchaCode> optionalCaptchaCode = captchaCodeRepository.findBySecretCode(captchaSecret);
            if (optionalCaptchaCode.isPresent()) {
                CaptchaCode captchaCode = optionalCaptchaCode.get();
                if (captchaCode.getCode().equals(captcha)) {
                    if (password.length() < 6) ar.addError("Пароль короче 6 символов");
                    else {
                        user.get().setPassword(password);
                        userRepository.save(user.get());
                    }
                } else ar.addError("Код с картинки введён неверно");
            } else {
                ar.addError("Ссылка для восстановления пароля устарела.\n" +
                        "\t\t\t\t<a href=\n" +
                        "\t\t\t\t\"/auth/restore\">Запросить ссылку снова</a>");
            }
        }
        return ar;
    }

    @Transactional
    public AuthResponse registerUser(String email, String password, String name, String captcha, String captchaSecret) {
        AuthResponse ar = new AuthResponse();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            ar.setResult(false);
            ar.addError("Этот e-mail уже зарегистрирован");
        } else {
            if (name.isEmpty()) {
                ar.addError("Имя указано неверно");
            } else if (password.length() < 6) {
                ar.addError("Пароль короче 6-ти символов");
            } else {
                Optional<CaptchaCode> optionalCaptchaCode = captchaCodeRepository.findBySecretCode(captchaSecret);
                if (optionalCaptchaCode.isPresent()) {
                    if (optionalCaptchaCode.get().getCode().equals(captcha)) {
                        User user = new User();
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setName(name);
                        user.setRegTime(LocalDateTime.now());
                        user.setModerator(false);
                        userRepository.save(user);
                    } else {
                        ar.addError("Код с картинки введён неверно");
                    }
                }
            }
        }
        return ar;
    }

    @Transactional
    public AuthResponse getCaptcha() {
        AuthResponse ar = new AuthResponse();
        CaptchaCode captchaCode = new CaptchaCode();
        Cage cage = new GCage();

        String code = cage.getTokenGenerator().next();
        String secretCode = UUID.randomUUID().toString();

        captchaCode.setCode(code);
        captchaCode.setSecretCode(secretCode);
        captchaCode.setTime(LocalDateTime.now());

        String image = Base64.getEncoder().encodeToString(cage.draw(code));
        //TODO add header
        ar.setImage(image);
        ar.setSecret(secretCode);
        return ar;
    }

    @Transactional
    public AuthResponse logoutUser() {
        AuthResponse ar = new AuthResponse();
        ar.setResult(true);

        appEnv.removeSession(RequestContextHolder.currentRequestAttributes().getSessionId());
        return ar;
    }

    public Optional<User> getAuthorizedUser() {
        final String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        if (isAuthorized(sessionId)) {
            Integer userId = appEnv.getUserIdFromSession(sessionId);
            return userRepository.findById(userId);
        }
        return Optional.empty();
    }

    private boolean isAuthorized(String sessionId) {
        return appEnv.getSessions().containsKey(sessionId);
    }
}
