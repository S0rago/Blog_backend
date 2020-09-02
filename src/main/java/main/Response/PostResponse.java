package main.Response;

import main.model.ModStatus;
import main.model.Post;
import main.model.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostResponse extends AbstractResponse {
    private Boolean responseStatus;
    private Set<String> errors;

    private Integer count;
    private List<Post> posts;

    private Integer id;
    private Integer isActive;
    private ModStatus modStatus;
    private User mod;
    private User user;
    private LocalDateTime time;
    private String title;
    private String text;
    private Integer viewCount;

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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
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
}
