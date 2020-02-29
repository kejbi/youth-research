package pl.kejbi.youthresearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.kejbi.youthresearch.controller.dto.TutorsGroupJoinRequestDTO;
import pl.kejbi.youthresearch.controller.dto.TutorsGroupRequest;
import pl.kejbi.youthresearch.model.AuthUser;
import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.model.TutorsGroup;
import pl.kejbi.youthresearch.model.TutorsGroupJoinRequest;
import pl.kejbi.youthresearch.service.TutorsGroupService;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/tutorsgroup")
@RequiredArgsConstructor
public class TutorsGroupController {

    private final TutorsGroupService tutorsGroupService;

    @PostMapping
    public TutorsGroup createTutorsGroup(@AuthenticationPrincipal AuthUser currentUser, @RequestBody @Valid TutorsGroupRequest tutorsGroupRequest, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            throw new ValidationException("bad name given");
        }
        Long tutorId = currentUser.getUser().getId();

        return tutorsGroupService.createGroup(tutorsGroupRequest.getName(), tutorId);
    }

    @PostMapping("/request/{groupId}")
    public TutorsGroupJoinRequestDTO createJoinRequest(@AuthenticationPrincipal AuthUser currentUser, @PathVariable Long groupId) {

        Member member = (Member) currentUser.getUser();
        TutorsGroupJoinRequest joinRequest = tutorsGroupService.createJoinRequest(groupId, member);

        return new TutorsGroupJoinRequestDTO(joinRequest);
    }
}
