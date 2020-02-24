package pl.kejbi.youthresearch.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.model.User;
import pl.kejbi.youthresearch.repository.MemberRepository;
import pl.kejbi.youthresearch.repository.TutorRepository;
import pl.kejbi.youthresearch.security.PasswordEncoder;

import javax.validation.ValidationException;

@AllArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;

    private final TutorRepository tutorRepository;

    private final PasswordEncoder passwordEncoder;

    private final String secret = "AwdGb3";

    @Transactional
    public User registerUser(String username, String name, String surname, String email, String password, String secret, boolean isTutor) {

        if(!isTutor) {
            Member member = new Member();
            member.setUsername(username);
            member.setName(name);
            member.setSurname(surname);
            member.setPassword(passwordEncoder.encode(password));
            member.setEmail(email);
            return memberRepository.save(member);
        }
        else if(secret.equals(this.secret)) {
            Tutor tutor = new Tutor();
            tutor.setUsername(username);
            tutor.setName(name);
            tutor.setSurname(surname);
            tutor.setPassword(passwordEncoder.encode(password));
            tutor.setEmail(email);
            return tutorRepository.save(tutor);
        }
        else {
            throw new ValidationException("bad secret");
        }
    }
}
