package pl.kejbi.youthresearch.exception;

public class InactiveResourceException extends RuntimeException {

    public InactiveResourceException(Class resourceClass, Long id) {
        super(resourceClass.getName() + " resource with id: " + id + " is inactive");
    }
}
