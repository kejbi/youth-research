package pl.kejbi.youthresearch.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagePollDTO {

    private List<PollDTO> polls;

    private int totalPages;
}
