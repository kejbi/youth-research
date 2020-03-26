package pl.kejbi.youthresearch.exception;

public class UsernameOrEmailTakenException extends RuntimeException {

    public UsernameOrEmailTakenException(String username, String email) {
        super("Username or email already taken: " + username + " or " + email);
    }
}
