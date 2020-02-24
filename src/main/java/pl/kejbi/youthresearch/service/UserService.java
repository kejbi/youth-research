package pl.kejbi.youthresearch.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kejbi.youthresearch.model.AuthUser;
import pl.kejbi.youthresearch.model.User;
import pl.kejbi.youthresearch.repository.MemberRepository;
import pl.kejbi.youthresearch.repository.TutorRepository;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final TutorRepository tutorRepository;

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user;
        if(tutorRepository.findTutorByUsername(s).isPresent()) {
            user = tutorRepository.findTutorByUsername(s).get();
        }
        else {
            user = memberRepository.findMemberByUsername(s).orElseThrow(() -> new UsernameNotFoundException("No username found"));
        }

        AuthUser userDetails = new AuthUser();
        userDetails.setUser(user);

        return userDetails;
    }
}
