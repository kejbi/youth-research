package pl.kejbi.youthresearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.kejbi.youthresearch.controller.dto.AnswerDTO;
import pl.kejbi.youthresearch.controller.dto.PagePollDTO;
import pl.kejbi.youthresearch.controller.dto.PollDTO;
import pl.kejbi.youthresearch.controller.dto.VoteDTO;
import pl.kejbi.youthresearch.model.*;
import pl.kejbi.youthresearch.service.PollService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/poll")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;

    @PostMapping
    public PollDTO createPoll(@AuthenticationPrincipal AuthUser user, @RequestBody @Valid PollDTO pollDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Tutor tutor = (Tutor) user.getUser();
        List<String> answers = pollDTO.getAnswers().stream()
                .map(AnswerDTO::getAnswer)
                .collect(Collectors.toList());

        Poll poll = pollService.createPoll(
                tutor.getId(),
                pollDTO.getTutorsGroupId(),
                pollDTO.getQuestion(),
                pollDTO.getStartDate(),
                pollDTO.getFinishDate(),
                answers
        );

        return new PollDTO(poll);
    }

    @DeleteMapping("/{pollId}")
    public void deletePoll(@AuthenticationPrincipal AuthUser user, @PathVariable Long pollId) {

        Tutor tutor = (Tutor) user.getUser();

        pollService.deletePoll(tutor.getId(), pollId);
    }

    @PutMapping
    public PollDTO voteInPoll(@AuthenticationPrincipal AuthUser user, @RequestBody @Valid VoteDTO voteDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Member member = (Member) user.getUser();
        Answer answer = pollService.voteInPoll(member.getId(), voteDTO.getAnswerId());
        Poll poll = answer.getPoll();
        return new PollDTO(poll);
    }

    @GetMapping
    @Secured({"ROLE_MEMBER", "ROLE_TUTOR"})
    public PagePollDTO getPollsByGroup(@RequestParam Long groupId, @RequestParam Integer page) {

        Page<Poll> pollsPage = pollService.getPollsByGroupId(groupId, page);
        PagePollDTO dto = new PagePollDTO();
        dto.setTotalPages(pollsPage.getTotalPages());
        List<PollDTO> polls = pollsPage.getContent().stream().map(PollDTO::new).collect(Collectors.toList());
        dto.setPolls(polls);

        return dto;
    }
}
