package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "is_active", columnDefinition = "TINYINT")
    private int isActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ModStatus modStatus;

    @Column(name = "moderator_id")
    private int modId;

    @NotNull
    @Column(name = "user_id")
    private int userId;

    @NotNull
    private Timestamp time;

    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    private int viewCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public ModStatus getModStatus() {
        return modStatus;
    }

    public void setModStatus(ModStatus modStatus) {
        this.modStatus = modStatus;
    }

    public int getModId() {
        return modId;
    }

    public void setModId(int modId) {
        this.modId = modId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
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

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
