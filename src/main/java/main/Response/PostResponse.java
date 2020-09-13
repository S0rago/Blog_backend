package main.Response;

import main.model.ModStatus;
import main.model.Post;
import main.model.PostComment;
import main.model.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostResponse implements Serializable {
    private Boolean responseStatus;
    private Set<String> errors;

    private Integer count;
    private List<Post> posts;

    private Integer id;
    private Boolean isActive;
    private ModStatus modStatus;
    private User mod;
    private User user;
    private LocalDateTime time;
    private String title;
    private String text;
    private Integer viewCount;
    private List<PostComment> comments;
    private Integer commentCount;

    public PostResponse() {

    }

    public Boolean getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Boolean responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Set<String> getErrors() {
        return errors;
    }

    public void setErrors(Set<String> errors) {
        this.errors = errors;
    }

    public void addError(String err) {
        if (errors == null) {
            errors = new HashSet<>();
        }
        errors.add(err);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ModStatus getModStatus() {
        return modStatus;
    }

    public void setModStatus(ModStatus modStatus) {
        this.modStatus = modStatus;
    }

    public User getMod() {
        return mod;
    }

    public void setMod(User mod) {
        this.mod = mod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
