package pl.kejbi.youthresearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.TutorsGroupJoinRequest;

@Repository
public interface TutorsGroupJoinRequestRepository extends JpaRepository<TutorsGroupJoinRequest, Long> {
}
