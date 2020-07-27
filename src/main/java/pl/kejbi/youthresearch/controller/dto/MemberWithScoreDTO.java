package pl.kejbi.youthresearch.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MemberWithScoreDTO {
    private Long memberId;
    private String name;
    private String surname;
    private Integer score;

    public MemberWithScoreDTO(Long memberId, String name, String surname, Integer score) {
        this.memberId = memberId;
        this.name = name;
        this.surname = surname;
        this.score = score;
    }
}
