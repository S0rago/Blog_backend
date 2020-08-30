package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

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
    @Column(name = "moderation_status", columnDefinition = "enum(\"NEW\",\"ACCEPTED\",\"DECLINED\")")
    private ModStatus modStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "moderator_id")
    private User mod;

    @NotNull
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Timestamp time;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String text;

    @NotNull
    @Column(name = "view_count")
    private int viewCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tag2post",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
