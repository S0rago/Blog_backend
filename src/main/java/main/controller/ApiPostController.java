package main.controller;

import main.Response.PostResponse;
import main.model.Post;
import main.service.ApiPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/post")
public class ApiPostController {

    @Autowired
    ApiPostService aps;

    @GetMapping(value = "")
    ResponseEntity<PostResponse> getPosts(@RequestParam int offset, @RequestParam int limit, @RequestParam String mode) {
        return new ResponseEntity<>(aps.getMainPosts(offset, limit, mode), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    ResponseEntity<PostResponse> getPostSearch(@RequestParam int offset, @RequestParam int limit, @RequestParam String query) {
        return new ResponseEntity<>(aps.getSearchPosts(offset, limit, query), HttpStatus.OK);
    }

    @GetMapping(value = "/byDate")
    ResponseEntity<PostResponse> getPostByDate(@RequestParam int offset, @RequestParam int limit, @RequestParam String date) {
        return new ResponseEntity<>(aps.getPostsByDate(offset, limit, date), HttpStatus.OK);
    }

    @GetMapping(value = "/byTag")
    ResponseEntity<PostResponse> getPostByTag(@RequestParam int offset, @RequestParam int limit, @RequestParam String tag) {
        return new ResponseEntity<>(aps.getPostsByTag(offset, limit, tag), HttpStatus.OK);
    }

    @GetMapping(value = "/moderation")
    ResponseEntity<PostResponse> getPostsToMod(@RequestParam int offset, @RequestParam int limit, @RequestParam String modStatus) {
        return new ResponseEntity<>(aps.getPostsToMod(offset, limit, modStatus), HttpStatus.OK);
    }

    @GetMapping(value = "/my")
    ResponseEntity<PostResponse> getMyPost(@RequestParam int offset, @RequestParam int limit, @RequestParam String status) {
        return new ResponseEntity<>(aps.getMyPosts(offset, limit, status), HttpStatus.OK);
    }

    @GetMapping(value = "/{ID}")
    ResponseEntity<PostResponse> getPostById(@PathVariable int id) {
        return new ResponseEntity<>(aps.getPostById(id),  HttpStatus.OK);
    }

    @PostMapping(value = "")
    ResponseEntity<PostResponse> addPost(@RequestBody Post post) {
        return new ResponseEntity<>(aps.saveNewPost(post), HttpStatus.OK);
    }

    @PostMapping(value = "/like")
    ResponseEntity<PostResponse> postLike(@RequestBody int postId) {
        return new ResponseEntity<>(aps.votePost(postId, true), HttpStatus.OK);
    }

    @PostMapping(value = "/dislike")
    ResponseEntity<PostResponse> postDislike(@RequestBody int postId) {
        return new ResponseEntity<>(aps.votePost(postId, false), HttpStatus.OK);
    }
}
