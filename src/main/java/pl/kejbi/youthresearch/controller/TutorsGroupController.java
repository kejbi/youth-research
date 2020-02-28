package pl.kejbi.youthresearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kejbi.youthresearch.controller.dto.TutorsGroupRequest;
import pl.kejbi.youthresearch.model.AuthUser;
import pl.kejbi.youthresearch.model.TutorsGroup;
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
}
