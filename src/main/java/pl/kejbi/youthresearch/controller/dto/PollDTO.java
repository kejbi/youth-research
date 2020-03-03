package pl.kejbi.youthresearch.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import pl.kejbi.youthresearch.model.Poll;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PollDTO {

    private Long id;

    @NotBlank
    @Length(min = 2, max = 300)
    private String question;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime finishDate;

    @NotNull
    private Long tutorsGroupId;

    @NotNull
    private List<AnswerDTO> answers;

    public PollDTO(Poll poll) {
        this.id = poll.getId();
        this.question = poll.getQuestion();
        this.startDate = poll.getStartDate();
        this.finishDate = poll.getFinishDate();
        this.tutorsGroupId = poll.getTutorsGroup().getId();
        this.answers = poll.getAnswers().stream().map(AnswerDTO::new).collect(Collectors.toList());
    }
}
