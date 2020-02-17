package pl.kejbi.youthresearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
}
