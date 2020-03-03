package pl.kejbi.youthresearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kejbi.youthresearch.model.Answer;
import pl.kejbi.youthresearch.model.Poll;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.model.TutorsGroup;
import pl.kejbi.youthresearch.repository.AnswerRepository;
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

    @Transactional
    public Poll createPoll(Long tutorId, Long tutorsGroupId, String question, LocalDateTime startTime, LocalDateTime finishTime, List<String> answers) {

        TutorsGroup tutorsGroup = tutorsGroupRepository.findById(tutorsGroupId).orElseThrow(RuntimeException::new);

        if (!tutorsGroup.getTutor().getId().equals(tutorId)) {
            throw new RuntimeException("Group does not belong to that tutor");
        }
        if (startTime.isAfter(finishTime)){
            throw new RuntimeException("start time must be before finish time");
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

        Poll poll = pollRepository.findById(pollId).orElseThrow(RuntimeException::new);
        Tutor tutor = poll.getTutorsGroup().getTutor();

        if(!tutor.getId().equals(tutorId)) {
            throw new RuntimeException("Poll does not belong to that tutor");
        }

        pollRepository.delete(poll);
    }
}
