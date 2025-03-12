package interview.mendel.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParentTransactionNotFoundException extends RuntimeException {
    public ParentTransactionNotFoundException(String message) {
        super("Parent transaction with id " + message + " not found");
    }
}
