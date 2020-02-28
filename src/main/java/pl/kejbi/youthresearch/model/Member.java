package pl.kejbi.youthresearch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutors_group_id")
    private TutorsGroup tutorsGroup;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "members"
    )
    private List<Answer> answers;


}
