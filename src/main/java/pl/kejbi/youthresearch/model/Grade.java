package pl.kejbi.youthresearch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tutorsgroup_id", nullable = false)
    private TutorsGroup tutorsGroup;

    public Grade(Integer score, String title, Member member, TutorsGroup tutorsGroup) {
        this.score = score;
        this.title = title;
        this.member = member;
        this.tutorsGroup = tutorsGroup;
    }
}
