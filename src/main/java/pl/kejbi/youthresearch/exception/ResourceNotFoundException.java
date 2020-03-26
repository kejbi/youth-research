package pl.kejbi.youthresearch.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(Class resourceClass,String attributeName, Object attribute) {
        super(resourceClass.getName() + " resource not found with attribute: (" + attributeName + ": " + attribute);
    }
}
