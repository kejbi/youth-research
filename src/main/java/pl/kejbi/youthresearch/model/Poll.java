package pl.kejbi.youthresearch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private LocalDateTime startDate;

    private LocalDateTime finishDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tutors_group_id", nullable = false)
    private TutorsGroup tutorsGroup;

    @OneToMany(mappedBy = "poll",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Answer> answers;
}
