package pl.kejbi.youthresearch.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.repository.MemberRepository;
import pl.kejbi.youthresearch.repository.TutorRepository;

import java.util.Map;

public class UserBuilder {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static Tutor buildTutor (Map<String,Object> props, TutorRepository repository) {

        Long id = props.containsKey("id") ? (Long) props.get("id") : 1L;
        String username = props.containsKey("username") ? (String) props.get("username") : "Tutor1";
        String name = props.containsKey("name") ? (String) props.get("name") : "Jan";
        String surname = props.containsKey("surname") ? (String) props.get("surname") : "Kowalski";
        String email = props.containsKey("email") ? (String) props.get("email") : "kowalski@kowal.pl";
        String password = props.containsKey("password") ? (String) props.get("password") : "tutor1";

        Tutor tutor = new Tutor();
        tutor.setId(id);
        tutor.setUsername(username);
        tutor.setName(name);
        tutor.setSurname(surname);
        tutor.setEmail(email);
        tutor.setPassword(passwordEncoder.encode(password));

        return repository.save(tutor);
    }

    public static Member buildMember (Map<String,Object> props, MemberRepository repository) {

        Long id = props.containsKey("id") ? (Long) props.get("id") : 1L;
        String username = props.containsKey("username") ? (String) props.get("username") : "Member1";
        String name = props.containsKey("name") ? (String) props.get("name") : "Jan";
        String surname = props.containsKey("surname") ? (String) props.get("surname") : "Nowak";
        String email = props.containsKey("email") ? (String) props.get("email") : "nowak@nowak.pl";
        String password = props.containsKey("password") ? (String) props.get("password") : "member1";

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);
        member.setName(name);
        member.setSurname(surname);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));

        return repository.save(member);
    }
}
