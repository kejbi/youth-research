package pl.kejbi.youthresearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kejbi.youthresearch.exception.ResourceNotFoundException;
import pl.kejbi.youthresearch.model.*;
import pl.kejbi.youthresearch.repository.MemberRepository;
import pl.kejbi.youthresearch.repository.TutorRepository;
import pl.kejbi.youthresearch.repository.TutorsGroupJoinRequestRepository;
import pl.kejbi.youthresearch.repository.TutorsGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TutorsGroupService {

    private final TutorsGroupRepository tutorsGroupRepository;

    private final TutorRepository tutorRepository;

    private final TutorsGroupJoinRequestRepository tutorsGroupJoinRequestRepository;

    @Transactional
    public TutorsGroup createGroup(String name, Long tutorId) {
        Tutor tutor = tutorRepository.findById(tutorId).orElseThrow(() -> new ResourceNotFoundException(Tutor.class, "id", tutorId));
        TutorsGroup group = new TutorsGroup();
        group.setName(name);
        group.setTutor(tutor);

        return tutorsGroupRepository.save(group);
    }

    @Transactional
    public TutorsGroupJoinRequest createJoinRequest(Long groupId, Member member) {
        TutorsGroup tutorsGroup = tutorsGroupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException(TutorsGroup.class, "id", groupId));
        TutorsGroupJoinRequest joinRequest = new TutorsGroupJoinRequest();
        joinRequest.setMember(member);
        joinRequest.setTutorsGroup(tutorsGroup);
        joinRequest.setAccepted(false);

        return tutorsGroupJoinRequestRepository.save(joinRequest);
    }

    @Transactional
    public TutorsGroupJoinRequest acceptJoinRequest(Long requestId) {
        TutorsGroupJoinRequest request = tutorsGroupJoinRequestRepository.findById(requestId).orElseThrow(() -> new ResourceNotFoundException(TutorsGroupJoinRequest.class, "id", requestId));
        request.setAccepted(true);
        TutorsGroup tutorsGroup = request.getTutorsGroup();
        Member member = request.getMember();
        tutorsGroup.addMember(member);
        tutorsGroupRepository.save(tutorsGroup);

        return tutorsGroupJoinRequestRepository.save(request);
    }

    public List<TutorsGroup> getGroupsByUser(User user) {
        if(user instanceof Tutor) {
            return tutorsGroupRepository.findAllByTutor((Tutor) user);
        }
        else {
            return tutorsGroupRepository.findAllByMembers((Member) user);
        }
    }

    public List<TutorsGroup> getJoinableGroupsForMember(Member member) {
        List<TutorsGroup> allGroups = tutorsGroupRepository.findAll();

        return allGroups.stream().filter(tutorsGroup -> !(tutorsGroup.getMembers().contains(member))).collect(Collectors.toList());
    }

    public List<TutorsGroupJoinRequest> getAllNotAcceptedRequestsByGroupId(Long tutorsGroupId) {

        return tutorsGroupJoinRequestRepository.findAllByAcceptedAndTutorsGroup_Id(false, tutorsGroupId);
    }
}
