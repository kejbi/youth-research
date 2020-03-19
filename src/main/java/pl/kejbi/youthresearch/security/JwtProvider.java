package pl.kejbi.youthresearch.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.kejbi.youthresearch.model.AuthUser;
import pl.kejbi.youthresearch.model.Tutor;

import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private static final String SECRET = "ABGDsdfst443__24t";

    private static final long JWT_TIME = 1000 * 120; //10 hours

    public String generateToken(Authentication auth) {
        AuthUser user = (AuthUser) auth.getPrincipal();

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + JWT_TIME);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .claim("role", user.getUser() instanceof Tutor ? "tutor" : "member")
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getRoleFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("role");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }
        catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        }
        catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        }
        catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        }
        catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        }
        catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
