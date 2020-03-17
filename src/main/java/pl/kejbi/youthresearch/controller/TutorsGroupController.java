package pl.kejbi.youthresearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.kejbi.youthresearch.controller.dto.TutorsGroupDTO;
import pl.kejbi.youthresearch.controller.dto.TutorsGroupJoinRequestDTO;
import pl.kejbi.youthresearch.controller.dto.TutorsGroupRequest;
import pl.kejbi.youthresearch.model.*;
import pl.kejbi.youthresearch.service.TutorsGroupService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tutorsgroup")
@RequiredArgsConstructor
public class TutorsGroupController {

    private final TutorsGroupService tutorsGroupService;

    @PostMapping
    @Secured("TUTOR")
    public TutorsGroupDTO createTutorsGroup(@AuthenticationPrincipal AuthUser currentUser, @RequestBody @Valid TutorsGroupRequest tutorsGroupRequest, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            throw new ValidationException("bad name given");
        }
        Long tutorId = currentUser.getUser().getId();

        TutorsGroup group = tutorsGroupService.createGroup(tutorsGroupRequest.getName(), tutorId);

        return new TutorsGroupDTO(group);
    }

    @PostMapping("/request")
    @Secured("MEMBER")
    public TutorsGroupJoinRequestDTO createJoinRequest(@AuthenticationPrincipal AuthUser currentUser, @RequestParam Long groupId) {

        Member member = (Member) currentUser.getUser();
        TutorsGroupJoinRequest joinRequest = tutorsGroupService.createJoinRequest(groupId, member);

        return new TutorsGroupJoinRequestDTO(joinRequest);
    }

    @PutMapping("/request/{requestId}")
    @Secured("TUTOR")
    public TutorsGroupJoinRequestDTO acceptJoinRequest(@PathVariable Long requestId) {

        TutorsGroupJoinRequest joinRequest = tutorsGroupService.acceptJoinRequest(requestId);

        return new TutorsGroupJoinRequestDTO(joinRequest);
    }

    @GetMapping("/my")
    @Secured({"TUTOR", "MEMBER"})
    public List<TutorsGroupDTO> getMyGroups(@AuthenticationPrincipal AuthUser currentUser) {

        User user = currentUser.getUser();
        List<TutorsGroup> groups = tutorsGroupService.getGroupsByUser(user);

        return groups.stream().map(TutorsGroupDTO::new).collect(Collectors.toList());
    }
}
