package pl.kejbi.youthresearch.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kejbi.youthresearch.model.Grade;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class GradeDTO {
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private Long groupId;

    @NotNull
    private String title;

    @NotNull
    private Integer score;

    public GradeDTO(Grade grade) {
        this.id = grade.getId();
        this.memberId = grade.getMember().getId();
        this.groupId = grade.getTutorsGroup().getId();
        this.title = grade.getTitle();
        this.score = grade.getScore();
    }
}
