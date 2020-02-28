package pl.kejbi.youthresearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.Tutor;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Optional<Tutor> findTutorByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);
}
