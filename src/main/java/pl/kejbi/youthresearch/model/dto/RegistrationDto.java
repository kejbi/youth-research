package pl.kejbi.youthresearch.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationDto {

    @NotBlank
    @Length(min = 2, max = 20)
    private String name;

    @NotBlank
    @Length(min = 2, max = 30)
    private String surname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 2, max = 20)
    private String password;

    @Length(max = 10)
    private String secret;

    @NotNull
    private boolean tutor;
}
