package pl.kejbi.youthresearch.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kejbi.youthresearch.model.TutorsGroupJoinRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TutorsGroupJoinRequestDTO {

    private Long tutorsGroupId;

    private Long memberId;

    public TutorsGroupJoinRequestDTO(TutorsGroupJoinRequest joinRequest) {
        this.memberId = joinRequest.getMember().getId();
        this.tutorsGroupId = joinRequest.getTutorsGroup().getId();
    }
}
