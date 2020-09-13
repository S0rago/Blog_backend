package main.repository;

import main.model.PostVote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostVoteRepository extends CrudRepository<PostVote, Integer> {
    Optional<PostVote> findByPostAndUser(Integer postId, Integer userId);
}
