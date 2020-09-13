package main.controller;

import main.Response.AuthResponse;
import main.service.ApiAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class ApiAuthController {
    @Autowired
    ApiAuthService aas;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestParam("e_mail") String email, @RequestParam String password) {
        return new ResponseEntity<>(aas.loginUser(email, password), HttpStatus.OK);
    }

    @GetMapping(value = "/check")
    public ResponseEntity<AuthResponse> check() {
        return new ResponseEntity<>(aas.checkUser(), HttpStatus.OK);
    }

    @PostMapping(value = "/restore")
    public ResponseEntity<AuthResponse> restore(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(aas.restorePass(email), HttpStatus.OK);
    }

    @PostMapping(value = "/password")
    public ResponseEntity<AuthResponse> changePass(@RequestParam String code, @RequestBody String password, @RequestParam String captcha, @RequestParam("captcha_secret") String captchaSecret) {
        return new ResponseEntity<>(aas.changePass(code, password, captcha, captchaSecret), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody String email, @RequestBody String password, @RequestBody String name, @RequestBody String captcha, @RequestBody String captchaSecret) {
        return new ResponseEntity<>(aas.registerUser(email, password, name, captcha, captchaSecret), HttpStatus.OK);
    }

    @PostMapping(value = "/captcha")
    public ResponseEntity<AuthResponse> getCaptcha() {
        return new ResponseEntity<>(aas.getCaptcha(), HttpStatus.OK);
    }
}
