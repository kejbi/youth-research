package pl.kejbi.youthresearch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TutorsGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            }
    )
    @JoinTable(
            name = "tutorsgroup_member",
            joinColumns = {@JoinColumn(name = "tutorsgroup_id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id")}
    )
    private List<Member> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @OneToMany(mappedBy = "tutorsGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "tutorsGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Poll> polls;

    public void addMember(Member member) {
        this.members.add(member);
    }

}
