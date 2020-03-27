package pl.kejbi.youthresearch.controller.dto;

import lombok.Getter;
import lombok.Setter;
import pl.kejbi.youthresearch.model.User;

@Getter
@Setter
public class UserDTO {

    private Long id;

    private String username;

    private String name;

    private String surname;

    private String email;

    public UserDTO(User user) {

        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
    }
}
