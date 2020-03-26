package pl.kejbi.youthresearch.exception;

public class BadSecretException extends RuntimeException {

    public BadSecretException() {
        super("Incorrect secret code");
    }
}
