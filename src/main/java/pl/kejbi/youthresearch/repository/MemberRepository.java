package pl.kejbi.youthresearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
