package pl.kejbi.youthresearch.exception;

public class NotYourResourceException extends RuntimeException {

    public NotYourResourceException(Class resourceClass, Long id) {
        super(resourceClass.getName() + " resource with id: " + id + " does not belong to you");
    }
}
