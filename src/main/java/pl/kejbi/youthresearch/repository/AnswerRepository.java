package pl.kejbi.youthresearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
