package pl.kejbi.youthresearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.model.TutorsGroup;

import java.util.List;

@Repository
public interface TutorsGroupRepository extends JpaRepository<TutorsGroup, Long> {

    List<TutorsGroup> findAllByMembers(Member member);

    List<TutorsGroup> findAllByTutor(Tutor tutor);
}
