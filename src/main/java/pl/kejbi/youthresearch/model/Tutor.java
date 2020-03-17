package pl.kejbi.youthresearch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tutor extends User {

    @OneToMany(mappedBy = "tutor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TutorsGroup> groups;

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Tutor)) {
            return false;
        }
        Tutor tutor = (Tutor) obj;

        return this.getId().equals(tutor.getId());
    }

}
