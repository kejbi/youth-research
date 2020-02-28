package pl.kejbi.youthresearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.model.TutorsGroup;
import pl.kejbi.youthresearch.repository.TutorRepository;
import pl.kejbi.youthresearch.repository.TutorsGroupRepository;


@Service
@RequiredArgsConstructor
public class TutorsGroupService {

    private final TutorsGroupRepository tutorsGroupRepository;

    private final TutorRepository tutorRepository;

    @Transactional
    public TutorsGroup createGroup(String name, Long tutorId) {
        //exception to do
        Tutor tutor = tutorRepository.findById(tutorId).orElseThrow(RuntimeException::new);

        TutorsGroup group = new TutorsGroup();
        group.setName(name);
        group.setTutor(tutor);

        return tutorsGroupRepository.save(group);
    }
}
