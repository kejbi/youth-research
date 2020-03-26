package pl.kejbi.youthresearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    Page<Poll> findAllByTutorsGroup_Id(Pageable pageable, Long tutorsGroupId);
}
