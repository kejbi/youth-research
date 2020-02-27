package pl.kejbi.youthresearch.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kejbi.youthresearch.model.AuthUser;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.repository.MemberRepository;
import pl.kejbi.youthresearch.repository.TutorRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final TutorRepository tutorRepository;

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<Tutor> user = tutorRepository.findTutorByUsername(s);

        return user.map(AuthUser::new)
                .orElseGet(() -> new AuthUser(memberRepository.findMemberByUsername(s).orElseThrow(() -> new UsernameNotFoundException("Username not found"))));

    }
}
