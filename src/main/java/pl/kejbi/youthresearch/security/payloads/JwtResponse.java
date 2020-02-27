package pl.kejbi.youthresearch.security.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {

    private String token;

    private String tokenType = "Bearer";
}
