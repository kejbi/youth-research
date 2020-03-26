package pl.kejbi.youthresearch.adviser;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.kejbi.youthresearch.exception.*;

import javax.validation.ValidationException;

@RestControllerAdvice
public class RESTAdviser {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationExceptionHandler(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());

        return errorResponse;
    }

    @ExceptionHandler(UsernameOrEmailTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse usernameOrEmailTakenExceptionHandler(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());

        return errorResponse;
    }

    @ExceptionHandler(BadSecretException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badSecretExceptionHandler(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());

        return errorResponse;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFoundExceptionHandler(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());

        return errorResponse;
    }

    @ExceptionHandler(NotYourResourceException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse notYourResourceExceptionHandler(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());

        return errorResponse;
    }

    @ExceptionHandler(InactiveResourceException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse inactiveResourceExceptionHandler(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());

        return errorResponse;
    }

    @ExceptionHandler(InvalidTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidTimeExceptionHandler(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());

        return errorResponse;
    }


}
