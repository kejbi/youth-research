package pl.kejbi.youthresearch.adviser;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.kejbi.youthresearch.exception.BadSecretException;
import pl.kejbi.youthresearch.exception.UsernameOrEmailTakenException;

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

}
