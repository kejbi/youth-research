package pl.kejbi.youthresearch.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.kejbi.youthresearch.controller.dto.GradeDTO;
import pl.kejbi.youthresearch.controller.dto.GradesListDTO;
import pl.kejbi.youthresearch.controller.dto.MemberWithScoreDTO;
import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.service.GradeService;
import pl.kejbi.youthresearch.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;
    private final Logger logger = LoggerFactory.getLogger(GradeController.class);
    private final UserService userService;

    @GetMapping("/total_score")
    public List<MemberWithScoreDTO> getMembersWithTotalScore(@RequestParam Long groupId) {
        List<Member> groupMembers = userService.getMembersFromGroup(groupId);

        return groupMembers.stream().map(member -> {
            return new MemberWithScoreDTO(
                    member.getId(),
                    member.getName(),
                    member.getSurname(),
                    gradeService.getMemberTotalScoreInGroup(member.getId(), groupId)
            );
        }).collect(Collectors.toList());
    }

    @GetMapping
    public List<GradeDTO> getMemberGrades(@RequestParam Long groupId, @RequestParam Long memberId) {
        return gradeService.getMemberGradesInGroup(memberId, groupId).stream().map(GradeDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/grouped")
    @Secured("ROLE_TUTOR")
    public List<GradeDTO> addAllGrades(@RequestBody GradesListDTO gradesListDTO) {
        return gradesListDTO.getGrades()
                .stream()
                .map(gradeDTO -> new GradeDTO(gradeService.addGrade(gradeDTO.getMemberId(), gradeDTO.getGroupId(), gradeDTO.getTitle(), gradeDTO.getScore())))
                .collect(Collectors.toList());
    }

    @PostMapping
    @Secured("ROLE_TUTOR")
    public GradeDTO addSingleGrade(@RequestBody GradeDTO gradeDTO) {
        return new GradeDTO(
            gradeService.addGrade(gradeDTO.getMemberId(), gradeDTO.getGroupId(), gradeDTO.getTitle(), gradeDTO.getScore())
        );
    }

    @DeleteMapping("/{id}")
    public void deleteSingleGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
    }

}

