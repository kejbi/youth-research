package pl.kejbi.youthresearch.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagePostDTO {

    private List<PostDTO> posts;

    private int totalPages;
}
