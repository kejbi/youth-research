package pl.kejbi.youthresearch.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GradesListDTO {
    private List<GradeDTO> grades;
}
