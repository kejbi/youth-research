package pl.kejbi.youthresearch.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kejbi.youthresearch.model.TutorsGroup;

@Getter
@Setter
@NoArgsConstructor
public class TutorsGroupDTO {

    private String name;

    private String tutorName;

    private String tutorSurname;

    public TutorsGroupDTO(TutorsGroup group) {
        this.name = group.getName();
        this.tutorName = group.getTutor().getName();
        this.tutorSurname = group.getTutor().getSurname();
    }
}
