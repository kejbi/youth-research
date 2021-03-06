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
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                CascadeType.MERGE,
                CascadeType.PERSIST
            }
    )
    @JoinTable(
            name = "answer_member",
            joinColumns = {@JoinColumn(name = "answer_id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id")}
    )
    private List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        members.add(member);
    }

    public void deleteMember(Member member) {
        members.remove(member);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Answer)) {
            return false;
        }
        Answer answer = (Answer) obj;

        return answer.getId().equals(this.getId());
    }
}
