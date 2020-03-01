package pl.kejbi.youthresearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.model.TutorsGroup;
import pl.kejbi.youthresearch.model.TutorsGroupJoinRequest;
import pl.kejbi.youthresearch.repository.MemberRepository;
import pl.kejbi.youthresearch.repository.TutorRepository;
import pl.kejbi.youthresearch.repository.TutorsGroupJoinRequestRepository;
import pl.kejbi.youthresearch.repository.TutorsGroupRepository;


@Service
@RequiredArgsConstructor
public class TutorsGroupService {

    private final TutorsGroupRepository tutorsGroupRepository;

    private final TutorRepository tutorRepository;

    private final TutorsGroupJoinRequestRepository tutorsGroupJoinRequestRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public TutorsGroup createGroup(String name, Long tutorId) {
        //exception to do
        Tutor tutor = tutorRepository.findById(tutorId).orElseThrow(RuntimeException::new);

        TutorsGroup group = new TutorsGroup();
        group.setName(name);
        group.setTutor(tutor);

        return tutorsGroupRepository.save(group);
    }

    @Transactional
    public TutorsGroupJoinRequest createJoinRequest(Long groupId, Member member) {
        //exception to do
        TutorsGroup tutorsGroup = tutorsGroupRepository.findById(groupId).orElseThrow(RuntimeException::new);

        TutorsGroupJoinRequest joinRequest = new TutorsGroupJoinRequest();
        joinRequest.setMember(member);
        joinRequest.setTutorsGroup(tutorsGroup);
        joinRequest.setAccepted(false);

        return tutorsGroupJoinRequestRepository.save(joinRequest);
    }

    @Transactional
    public TutorsGroupJoinRequest acceptJoinRequest(Long requestId) {

        TutorsGroupJoinRequest request = tutorsGroupJoinRequestRepository.findById(requestId).orElseThrow(RuntimeException::new);
        request.setAccepted(true);
        TutorsGroup tutorsGroup = request.getTutorsGroup();
        Member member = request.getMember();
        member.setTutorsGroup(tutorsGroup);

        memberRepository.save(member);

        return tutorsGroupJoinRequestRepository.save(request);
    }
}
