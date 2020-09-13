package main.dto.users;

import main.model.User;

public class AuthUser {
    private Integer id;
    private Boolean moderation;
    private String name;
    private String email;
    private String photo;
    private Integer moderationCount;
    private Boolean settings;

    public AuthUser() {
    }

    public AuthUser(User user) {
        id = user.getId();
        moderation = user.isModerator();
        name = user.getName();
        email = user.getEmail();
        photo = user.getPhoto();
        moderationCount = 0;
        settings = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getModeration() {
        return moderation;
    }

    public void setModeration(Boolean moderation) {
        this.moderation = moderation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getModerationCount() {
        return moderationCount;
    }

    public void setModerationCount(Integer moderationCount) {
        this.moderationCount = moderationCount;
    }

    public Boolean getSettings() {
        return settings;
    }

    public void setSettings(Boolean settings) {
        this.settings = settings;
    }
}
