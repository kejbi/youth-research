package pl.kejbi.youthresearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);
}
