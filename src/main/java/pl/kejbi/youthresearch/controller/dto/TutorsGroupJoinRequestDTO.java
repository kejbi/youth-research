package pl.kejbi.youthresearch.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kejbi.youthresearch.model.TutorsGroupJoinRequest;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TutorsGroupJoinRequestDTO {

    @NotBlank
    private Long tutorsGroupId;

    @NotBlank
    private Long memberId;

    private String groupName;

    private String username;

    private boolean accepted;

    public TutorsGroupJoinRequestDTO(TutorsGroupJoinRequest joinRequest) {
        this.memberId = joinRequest.getMember().getId();
        this.groupName = joinRequest.getTutorsGroup().getName();
        this.username = joinRequest.getMember().getUsername();
        this.tutorsGroupId = joinRequest.getTutorsGroup().getId();
        this.accepted = joinRequest.isAccepted();
    }
}
