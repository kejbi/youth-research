package pl.kejbi.youthresearch.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import pl.kejbi.youthresearch.model.Answer;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDTO {

    private Long id;

    @NotBlank
    @Length(min = 2, max = 100)
    private String answer;

    private Integer votes;

    private boolean checked = false;

    public AnswerDTO(Answer answer) {
        this.id = answer.getId();
        this.answer = answer.getAnswer();
        this.votes = answer.getMembers().size();
    }
}
