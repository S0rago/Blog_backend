package main.repository;

import main.model.ModStatus;
import main.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    //recent
    @Query(value = "select * from posts " +
            "where is_active = 1 and moderation_status = 'ACCEPTED' " +
            "and time <= current_timestamp() " +
            "order by time desc " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findAcceptedActiveRecentPosts(int offset, int limit);

    //popular
    @Query(value = "select posts.*, count(post_comments.post_id) AS com_count FROM posts " +
            "left join post_comments on posts.id = post_comments.post_id " +
            "where is_active = 1 and moderation_status = 'ACCEPTED' " +
            "and posts.time <= current_timestamp() " +
            "group by posts.id " +
            "order by comment_count DESC " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findAcceptedActivePopularPosts(int offset, int limit);

    //best
    @Query(value = "select * from posts " +
            "where is_active = 1 and moderation_status = 'ACCEPTED' " +
            "and time <= current_timestamp() " +
            "order by count(votes) where votes = 1 desc " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findAcceptedActiveBestPosts(int offset, int limit);

    //early
    @Query(value = "select * from posts " +
            "where is_active = 1 and moderation_status = 'ACCEPTED' " +
            "and time <= current_timestamp() " +
            "order by time " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findAcceptedActiveEarlyPosts(int offset, int limit);

    @Query(value = "select * from posts " +
            "where is_active = 1 and moderation_status = 'ACCEPTED' " +
            "and time <= current_timestamp() and upper(title) like concat('%', upper(:query), '%') " +
            "order by time desc" +
            "limit :offset, :limit",
            nativeQuery = true)
    List<Post> findQueryMatchPosts(int offset, int limit, @Param("query") String query);

    @Query(value = "select * from posts " +
            "where is_active = 1 and moderation_status = 'ACCEPTED' " +
            "and time <= current_timestamp() and time between ?3 and ?4" +
            "order by time desc" +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findPostsByDate(int offset, int limit, String from, String to);

    @Query(value = "select * from posts " +
            "where is_active = 1 and moderation_status = 'ACCEPTED' " +
            "and time <= current_timestamp() and find_in_set(?3, tags) > 0 " +
            "order by time desc" +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findPostsByTag(int offset, int limit, String tag);

    @Query(value = "select * from posts " +
            "where is_active = 1 and moderation_status = '?4' " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findNewPostsToMod(int offset, int limit, String modStatus);

    @Query(value = "select * from posts " +
            "where is_active = 1 and moderation_status = '?4' " +
            "and moderator_id = ?3 " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findPostsToMod(int offset, int limit, int modId, String modStatus);

    @Query(value = "select * from posts " +
            "where is_active = 1 and moderation_status = 'ACCEPTED' " +
            "and time <= current_timestamp() and id = ?3",
            nativeQuery = true)
    Optional<Post> findPostById(int id);

    @Query(value = "select * from posts " +
            "where user_id = ?3 and is_active = ?4 and moderation_status = '?5' " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findMyPosts(int offset, int limit, int userId, int isActive, String modStatus);

    @Query(value = "select count(*) from posts " +
            "where is_active = 1 and moderation_status = 'ACCEPTED' ",
            nativeQuery = true)
    Integer countPostsToMod();
}