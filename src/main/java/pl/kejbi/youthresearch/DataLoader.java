package pl.kejbi.youthresearch;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.model.TutorsGroup;
import pl.kejbi.youthresearch.repository.MemberRepository;
import pl.kejbi.youthresearch.repository.TutorRepository;
import pl.kejbi.youthresearch.repository.TutorsGroupRepository;
import pl.kejbi.youthresearch.security.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final TutorRepository tutorRepository;

    private final MemberRepository memberRepository;

    private final TutorsGroupRepository tutorsGroupRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Tutor tutor1 = new Tutor();
        tutor1.setName("Mariusz");
        tutor1.setSurname("Trąbalski");
        tutor1.setEmail("tutor1@x.pl");
        tutor1.setUsername("tutor1");
        tutor1.setPassword(passwordEncoder.encode("tutor1"));
        tutor1 = tutorRepository.save(tutor1);

        Tutor tutor2 = new Tutor();
        tutor2.setName("Szymon");
        tutor2.setSurname("Burger");
        tutor2.setEmail("tutor2@x.pl");
        tutor2.setUsername("tutor2");
        tutor2.setPassword(passwordEncoder.encode("tutor2"));
        tutor2 = tutorRepository.save(tutor2);

        Member member1 = new Member();
        member1.setName("Kacper");
        member1.setSurname("Chodzajski");
        member1.setEmail("member1@x.pl");
        member1.setUsername("member1");
        member1.setPassword(passwordEncoder.encode("member1"));
        member1 = memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("Daria");
        member2.setSurname("Ljubowaja");
        member2.setEmail("member2@x.pl");
        member2.setUsername("member2");
        member2.setPassword(passwordEncoder.encode("member2"));
        member2 = memberRepository.save(member2);

        TutorsGroup group1 = new TutorsGroup();
        group1.setName("Grupa Mariusza");
        group1.setTutor(tutor1);
        group1 = tutorsGroupRepository.save(group1);

        group1.addMember(member1);
        group1.addMember(member2);
        group1 = tutorsGroupRepository.save(group1);
    }
}