package pl.kejbi.youthresearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kejbi.youthresearch.exception.ResourceNotFoundException;
import pl.kejbi.youthresearch.model.Grade;
import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.model.TutorsGroup;
import pl.kejbi.youthresearch.repository.GradeRepository;
import pl.kejbi.youthresearch.repository.MemberRepository;
import pl.kejbi.youthresearch.repository.TutorsGroupRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;

    private final MemberRepository memberRepository;

    private final TutorsGroupRepository tutorsGroupRepository;

    @Transactional
    public Grade addGrade(long userId, long groupId, String title, int score) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(Member.class, "id", userId));
        TutorsGroup group = tutorsGroupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException(TutorsGroup.class, "id", groupId));
        Grade grade = new Grade(score, title, member, group);

        return gradeRepository.save(grade);
    }

    @Transactional
    public void deleteGrade(long gradeId) {
        gradeRepository.deleteById(gradeId);
    }

    public int getMemberTotalScoreInGroup(long memberId, long groupId) {
        List<Grade> memberGrades = gradeRepository.findAllByMember_IdAndTutorsGroup_Id(memberId, groupId);
        int sumScore = 0;
        for (Grade grade: memberGrades) {
            sumScore += grade.getScore();
        }

        return sumScore;
    }

    public List<Grade> getMemberGradesInGroup(long memberId, long groupId) {
        return gradeRepository.findAllByMember_IdAndTutorsGroup_Id(memberId, groupId);
    }



}
