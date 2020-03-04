package pl.kejbi.youthresearch.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class VoteDTO {

    @NotNull
    private Long answerId;
}
