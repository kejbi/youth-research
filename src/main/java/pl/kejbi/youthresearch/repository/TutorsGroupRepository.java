package pl.kejbi.youthresearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.TutorsGroup;

@Repository
public interface TutorsGroupRepository extends JpaRepository<TutorsGroup, Long> {
}
