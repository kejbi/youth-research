package pl.kejbi.youthresearch.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class TutorsGroupRequest {

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

}
