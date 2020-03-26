package pl.kejbi.youthresearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kejbi.youthresearch.exception.NotYourResourceException;
import pl.kejbi.youthresearch.exception.ResourceNotFoundException;
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

        TutorsGroup tutorsGroup = tutorGroupRepository.findById(tutorsGroupId).orElseThrow(() -> new ResourceNotFoundException(TutorsGroup.class, "id", tutorsGroupId));

        if (!tutorsGroup.getTutor().getId().equals(tutorId)) {
            throw new NotYourResourceException(TutorsGroup.class, tutorsGroupId);
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

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(Post.class, "id", postId));
        Tutor tutor = post.getTutorsGroup().getTutor();

        if(!tutor.getId().equals(tutorId)) {
            throw new NotYourResourceException(Post.class, postId);
        }

        postRepository.delete(post);
    }

    public Page<Post> getPostsByGroupId(Long groupId, Integer page) {

        Pageable pageable = PageRequest.of(page-1, 5, Sort.by("date").descending());

        return postRepository.findAllByTutorsGroup_Id(pageable, groupId);
    }
}
