package pl.kejbi.youthresearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.kejbi.youthresearch.controller.dto.PostDTO;
import pl.kejbi.youthresearch.model.AuthUser;
import pl.kejbi.youthresearch.model.Post;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.service.PostService;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostDTO createPost(@AuthenticationPrincipal AuthUser user, @RequestBody @Valid PostDTO postDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException("bad data in post dto");
        }

        Tutor tutor = (Tutor) user.getUser();
        Post post = postService.createPost(
                tutor.getId(),
                postDto.getGroupId(),
                postDto.getTitle(),
                postDto.getPost()
        );

        return new PostDTO(post);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@AuthenticationPrincipal AuthUser user, @PathVariable Long postId) {

        Tutor tutor = (Tutor) user.getUser();
        postService.deletePost(tutor.getId(), postId);
    }
}
