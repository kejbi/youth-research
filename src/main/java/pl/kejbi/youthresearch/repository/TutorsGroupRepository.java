package pl.kejbi.youthresearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.model.TutorsGroup;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorsGroupRepository extends JpaRepository<TutorsGroup, Long> {

    List<TutorsGroup> findAllByMembers(Member member);

    List<TutorsGroup> findAllByTutor(Tutor tutor);

    @Query("SELECT g FROM TutorsGroup g JOIN FETCH g.members WHERE g.id = (:id)")
    Optional<TutorsGroup> findByIdWithMemberFetched(@Param("id") Long id);

}
