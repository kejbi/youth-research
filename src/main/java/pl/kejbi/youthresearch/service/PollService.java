package pl.kejbi.youthresearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kejbi.youthresearch.exception.InactiveResourceException;
import pl.kejbi.youthresearch.exception.InvalidTimeException;
import pl.kejbi.youthresearch.exception.NotYourResourceException;
import pl.kejbi.youthresearch.exception.ResourceNotFoundException;
import pl.kejbi.youthresearch.model.*;
import pl.kejbi.youthresearch.repository.AnswerRepository;
import pl.kejbi.youthresearch.repository.MemberRepository;
import pl.kejbi.youthresearch.repository.PollRepository;
import pl.kejbi.youthresearch.repository.TutorsGroupRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PollService {

    private final PollRepository pollRepository;

    private final AnswerRepository answerRepository;

    private final TutorsGroupRepository tutorsGroupRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Poll createPoll(Long tutorId, Long tutorsGroupId, String question, LocalDateTime startTime, LocalDateTime finishTime, List<String> answers) {

        TutorsGroup tutorsGroup = tutorsGroupRepository.findById(tutorsGroupId).orElseThrow(() -> new ResourceNotFoundException(TutorsGroup.class, "id", tutorsGroupId));

        if (!tutorsGroup.getTutor().getId().equals(tutorId)) {
            throw new NotYourResourceException(TutorsGroup.class, tutorsGroupId);
        }
        if (startTime.isAfter(finishTime)){
            throw new InvalidTimeException("Start time must be before finish time");
        }

        Poll poll = new Poll();
        poll.setQuestion(question);
        poll.setTutorsGroup(tutorsGroup);
        poll.setStartDate(startTime);
        poll.setFinishDate(finishTime);
        Poll savedPoll = pollRepository.save(poll);

        List<Answer> answerList = answers.stream()
                .map(answer -> createAnswer(savedPoll, answer))
                .collect(Collectors.toList());

        answerRepository.saveAll(answerList);
        savedPoll.setAnswers(answerList);

        return savedPoll;

    }

    private Answer createAnswer(Poll poll, String answer) {

        Answer createdAnswer = new Answer();
        createdAnswer.setAnswer(answer);
        createdAnswer.setPoll(poll);

        return createdAnswer;
    }

    @Transactional
    public void deletePoll(Long tutorId, Long pollId) {

        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new ResourceNotFoundException(Poll.class, "id", pollId));
        Tutor tutor = poll.getTutorsGroup().getTutor();

        if(!tutor.getId().equals(tutorId)) {
            throw new NotYourResourceException(Poll.class, poll.getId());
        }

        pollRepository.delete(poll);
    }

    @Transactional
    public Answer voteInPoll(Long memberId, Long answerId) {

        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new ResourceNotFoundException(Answer.class, "id", answerId));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(Member.class, "id", memberId));
        Poll poll = answer.getPoll();
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(poll.getStartDate()) || now.isAfter(poll.getFinishDate())) {
            throw new InactiveResourceException(Poll.class, poll.getId());
        }
        if (member.getAnswers().contains(answer)) {
            return answer;
        }

        List<Answer> answers = poll.getAnswers();
        answers.forEach(ans -> {
            if (ans.getMembers().contains(member)) {
                ans.deleteMember(member);
                answerRepository.save(ans);
            }
        });

        answer.addMember(member);

        return answerRepository.save(answer);
    }

    public Page<Poll> getPollsByGroupId(Long groupId, Integer page) {

        Pageable pageable = PageRequest.of(page-1, 5, Sort.by("finishDate").descending());

        return pollRepository.findAllByTutorsGroup_Id(pageable, groupId);
    }
}
