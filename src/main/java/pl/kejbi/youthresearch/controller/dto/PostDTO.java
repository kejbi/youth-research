package pl.kejbi.youthresearch.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import pl.kejbi.youthresearch.model.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Long id;

    @NotBlank
    @Length(min = 1, max = 100)
    private String title;

    @NotBlank
    @Length
    private String post;

    @NotNull
    private Long groupId;

    private LocalDateTime date;

    private String tutorName;

    private String tutorSurname;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.post = post.getPost();
        this.date = post.getDate();
        this.tutorName = post.getTutorsGroup().getTutor().getName();
        this.tutorSurname = post.getTutorsGroup().getTutor().getSurname();
    }
}
