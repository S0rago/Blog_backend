package main.service;

import main.AppEnvironment;
import main.Response.PostResponse;
import main.model.ModStatus;
import main.model.Post;
import main.model.PostVote;
import main.model.User;
import main.repository.PostCommentRepository;
import main.repository.PostRepository;
import main.repository.PostVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApiPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PostVoteRepository postVoteRepository;

    @Autowired
    private ApiAuthService aas;

    @Autowired
    private AppEnvironment appEnv;

    public ApiPostService(PostRepository postRepository, PostCommentRepository postCommentRepository, AppEnvironment appEnv) {
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
        this.appEnv = appEnv;
    }

    public PostResponse getMainPosts(int offset, int limit, String mode) {
        PostResponse pr = new PostResponse();
        List<Post> posts;
        switch (mode) {
            case "recent":
                posts = postRepository.findAcceptedActiveRecentPosts(offset, limit);
                break;
            case "popular":
                posts = postRepository.findAcceptedActivePopularPosts(offset, limit);
                break;
            case "best":
                posts = postRepository.findAcceptedActiveBestPosts(offset, limit);
                break;
            case "early":
                posts = postRepository.findAcceptedActiveEarlyPosts(offset, limit);
                break;
            default:
                posts = Collections.emptyList();
        }

        pr.setCount(posts.size());
        pr.setPosts(posts);
        return pr;
    }

    public PostResponse saveNewPost(Post post) {
        Optional<User> optUser = aas.getAuthorizedUser();
        PostResponse pr = new PostResponse();
        if (optUser.isPresent()) {
            String title = post.getTitle();
            String text = post.getText();
            if (title.length() < 3) {
                if (title.isEmpty()) {
                    pr.addError("Заголовок не указан");
                } else {
                    pr.addError("Заголовок слишком короткий");
                }
            }
            if (text.length() < 50) {
                if (text.isEmpty()) {
                    pr.addError("Текст публикации отсутствует");
                } else {
                    pr.addError("Текст публикации слишком короткий");
                }
            }
            LocalDateTime publishDate = post.getTime();
            if (publishDate.isBefore(LocalDateTime.now())) {
                post.setTime(LocalDateTime.now());
            }
            post.setUser(optUser.get());
            post.setModStatus(ModStatus.NEW);
            post.setViewCount(0);
            postRepository.save(post);
        } else {
            //TODO 401
        }
        return pr;
    }

    public PostResponse getSearchPosts(int offset, int limit, String query) {
        PostResponse pr = new PostResponse();
        List<Post> posts = postRepository.findQueryMatchPosts(offset, limit, query);
        pr.setCount(posts.size());
        pr.setPosts(posts);
        return pr;
    }

    public PostResponse getPostsByDate(int offset, int limit, String date) {
        PostResponse pr = new PostResponse();
        String from = date + " 00:00:00";
        String to = date + " 23:59:59";
        List<Post> posts = postRepository.findPostsByDate(offset, limit, from, to);
        pr.setCount(posts.size());
        pr.setPosts(posts);
        return pr;
    }

    public PostResponse getPostsByTag(int offset, int limit, String tag) {
        PostResponse pr = new PostResponse();
        List<Post> posts = postRepository.findPostsByTag(offset, limit, tag);
        pr.setCount(posts.size());
        pr.setPosts(posts);
        return pr;
    }

    public PostResponse getPostById(int id) {
        PostResponse pr = new PostResponse();
        Optional<Post> optPost = postRepository.findPostById(id);
        if (optPost.isPresent()) {
            Post post = optPost.get();
            post.setViewCount(post.getViewCount() + 1);
            postRepository.save(post);

            pr.setId(post.getId());
            pr.setIsActive(post.getIsActive() == 1);
            pr.setUser(post.getUser());
            pr.setTime(post.getTime());
            pr.setTitle(post.getTitle());
            pr.setText(post.getText());
            pr.setViewCount(post.getViewCount());
            pr.setComments(post.getComments());
        }
        return pr;
    }

    public PostResponse getMyPosts(int offset, int limit, String status) {
        PostResponse pr = new PostResponse();
        List<Post> posts;
        switch (status) {
            case "inactive":
                posts = postRepository.findMyPosts(offset, limit, 0, 0, ModStatus.NEW.getStatus());
                break;
            case "pending":
                posts = postRepository.findMyPosts(offset, limit, 0, 1, ModStatus.NEW.getStatus());
                break;
            case "declined":
                posts = postRepository.findMyPosts(offset, limit, 0, 1, ModStatus.DECLINED.getStatus());
                break;
            case "published":
                posts = postRepository.findMyPosts(offset, limit, 0, 1, ModStatus.ACCEPTED.getStatus());
                break;
            default:
                posts = Collections.emptyList();
        }
        pr.setCount(posts.size());
        pr.setPosts(posts);
        return pr;
    }

    public PostResponse getPostsToMod(int offset, int limit, String modStatus) {
        PostResponse pr = new PostResponse();
        List<Post> posts = new ArrayList<>();
        Optional<User> optUser = aas.getAuthorizedUser();
        if (optUser.isPresent()) {
            User mod = optUser.get();
            if (mod.isModerator()) {
                switch (modStatus) {
                    case "new":
                        posts = postRepository.findNewPostsToMod(offset, limit, ModStatus.NEW.getStatus());
                        break;
                    case "declined":
                        posts = postRepository.findPostsToMod(offset, limit, 1, ModStatus.DECLINED.getStatus());
                        break;
                    case "accepted":
                        posts = postRepository.findPostsToMod(offset, limit, 1, ModStatus.ACCEPTED.getStatus());
                        break;
                    default:
                        posts = Collections.emptyList();
                }
            } else {
                //TODO 401
            }
        } else {
            //TODO 401
        }
        pr.setCount(posts.size());
        pr.setPosts(posts);
        return pr;
    }

    public PostResponse votePost(int postId, boolean vote) {
        PostResponse pr = new PostResponse();
        Optional<Post> optPost = postRepository.findPostById(postId);
        Optional<User> optUser = aas.getAuthorizedUser();

        if (optUser.isPresent()) {
            if (optPost.isPresent()) {
                Optional<PostVote> optPostVote = postVoteRepository.findByPostAndUser(postId, optUser.get().getId());
                if (optPostVote.isPresent()) {
                    PostVote pv = optPostVote.get();
                    if (pv.getValue() == vote) {
                        pr.setResponseStatus(false);
                    } else {
                        pv.setValue(!pv.getValue());
                        pr.setResponseStatus(true);
                    }
                } else {
                    PostVote pv = new PostVote();
                    pv.setPost(optPost.get());
                    pv.setUser(optUser.get());
                    pv.setTime(LocalDateTime.now());
                    pv.setValue(vote);
                    pr.setResponseStatus(true);
                }
            }
            //TODO 401
        }
        return pr;
    }
}
