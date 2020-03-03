package pl.kejbi.youthresearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kejbi.youthresearch.model.Post;
import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.model.TutorsGroup;
import pl.kejbi.youthresearch.repository.PostRepository;
import pl.kejbi.youthresearch.repository.TutorsGroupRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final TutorsGroupRepository tutorGroupRepository;

    @Transactional
    public Post createPost(Long tutorId, Long tutorsGroupId, String title, String postText) {

        TutorsGroup tutorsGroup = tutorGroupRepository.findById(tutorsGroupId).orElseThrow(RuntimeException::new);

        if (!tutorsGroup.getTutor().getId().equals(tutorId)) {
            throw new RuntimeException("Group does not belong to that tutor");
        }

        Post post = new Post();
        post.setTitle(title);
        post.setPost(postText);
        post.setTutorsGroup(tutorsGroup);
        post.setDate(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long tutorId, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        Tutor tutor = post.getTutorsGroup().getTutor();

        if(!tutor.getId().equals(tutorId)) {
            throw new RuntimeException("Post does not belong to that tutor");
        }

        postRepository.delete(post);
    }
}
