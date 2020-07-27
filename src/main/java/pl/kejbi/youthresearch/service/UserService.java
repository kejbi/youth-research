package pl.kejbi.youthresearch.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kejbi.youthresearch.exception.ResourceNotFoundException;
import pl.kejbi.youthresearch.model.AuthUser;
import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.model.TutorsGroup;
import pl.kejbi.youthresearch.repository.MemberRepository;
import pl.kejbi.youthresearch.repository.TutorRepository;
import pl.kejbi.youthresearch.repository.TutorsGroupRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final TutorRepository tutorRepository;

    private final MemberRepository memberRepository;

    private final TutorsGroupRepository tutorsGroupRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<Tutor> user = tutorRepository.findTutorByUsername(s);

        return user.map(AuthUser::new)
                .orElseGet(() -> new AuthUser(memberRepository.findMemberByUsername(s).orElseThrow(() -> new UsernameNotFoundException("Username not found"))));

    }

    public List<Member> getMembersFromGroup(Long groupId) {
        return memberRepository.findAllByTutorsGroups(tutorsGroupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException(TutorsGroup.class, "id", groupId)));
    }
}
