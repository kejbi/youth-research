package pl.kejbi.youthresearch.security.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    @Length(min = 2, max = 20)
    private String username;

    @NotBlank
    @Length(min = 2, max = 20)
    private String password;
}
