package main.service;

import main.Response.AbstractResponse;
import main.Response.PostResponse;
import main.model.ModStatus;
import main.model.Post;
import main.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ApiPostService {

    @Autowired
    PostRepository postRepository;

    public AbstractResponse getMainPosts(int offset, int limit, String mode) {
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

    public AbstractResponse saveNewPost(Post post) {
        String title = post.getTitle();
        String text = post.getText();
        PostResponse pr = new PostResponse();
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
        post.setModStatus(ModStatus.NEW);
        post.setViewCount(0);
        //TODO Auth check
        postRepository.save(post);
        return pr;
    }
}
