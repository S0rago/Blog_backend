package main.repository;

import main.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    //recent
    @Query(value = "select p from Post p " +
            "where p.isActive = 1 and p.modStatus = main.model.modStatus.ACCEPTED " +
            "and p.time <= current_timestamp() " +
            "order by p.time desc " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findAcceptedActiveRecentPosts(int offset, int limit);

    //popular
    @Query(value = "select p from Post p " +
            "where p.isActive = 1 and p.modStatus = main.model.modStatus.ACCEPTED " +
            "and p.time <= current_timestamp() " +
            "order by count(p.comments) desc " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findAcceptedActivePopularPosts(int offset, int limit);

    //best
    @Query(value = "select p from Post p " +
            "where p.isActive = 1 and modStatus = main.model.modStatus.ACCEPTED " +
            "and p.time <= current_timestamp() " +
            "order by count(p.votes) where p.votes = 1 desc " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findAcceptedActiveBestPosts(int offset, int limit);

    //early
    @Query(value = "select p from Post p " +
            "where p.isActive = 1 and modStatus = main.model.modStatus.ACCEPTED " +
            "and p.time <= current_timestamp() " +
            "order by p.time " +
            "limit ?1, ?2",
            nativeQuery = true)
    List<Post> findAcceptedActiveEarlyPosts(int offset, int limit);
}