package main.repository;

import main.model.CaptchaCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaptchaCodeRepository extends CrudRepository<CaptchaCode, Integer> {
    Optional<CaptchaCode> findBySecretCode(String secretCode);
}
