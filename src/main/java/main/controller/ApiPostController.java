package main.controller;

import main.model.ModStatus;
import main.model.Post;
import main.service.ApiPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.format.annotation.DateTimeFormat.*;

@Controller
@RequestMapping("/api/post")
public class ApiPostController {

    @Autowired
    ApiPostService aps;

    @GetMapping(value = "")
    ResponseEntity getPosts(@RequestParam int offset, @RequestParam int limit, @RequestParam String mode) {
            return new ResponseEntity(aps.getMainPosts(offset, limit, mode), HttpStatus.OK);
        //TODO
    }

    @GetMapping(value = "/search")
    ResponseEntity getPostSearch(@RequestParam int offset, @RequestParam int limit, @RequestParam String query) {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
        //TODO
    }

    @GetMapping(value = "/byDate")
    ResponseEntity getPostByDate(@RequestParam int offset, @RequestParam int limit, @RequestParam @DateTimeFormat(iso = ISO.DATE) Date date) {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
        //TODO
    }

    @GetMapping(value = "/byTag")
    ResponseEntity getPostByTag(@RequestParam int offset, @RequestParam int limit, @RequestParam String tag) {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
        //TODO
    }

    @GetMapping(value = "/moderation")
    ResponseEntity getPostToMod(@RequestParam int offset, @RequestParam int limit, @RequestParam String modStatus) {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
        //TODO
    }

    @GetMapping(value = "/my")
    ResponseEntity getMyPost(@RequestParam int offset, @RequestParam int limit, @RequestParam boolean isActive, @RequestParam ModStatus modStatus) {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
        //TODO
    }

    @GetMapping(value = "/{ID}")
    ResponseEntity getPostById(@PathVariable int id) {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
        //TODO
    }

    @PostMapping(value = "")
    ResponseEntity addPost(@RequestBody Post post) {
        return new ResponseEntity<>(aps.saveNewPost(post), HttpStatus.OK);
    }

    @PostMapping(value = "/like")
    ResponseEntity postLike() {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
        //TODO
    }

    @PostMapping(value = "/dislike")
    ResponseEntity postDislike() {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
