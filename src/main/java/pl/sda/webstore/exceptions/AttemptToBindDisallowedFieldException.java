package pl.sda.webstore.exceptions;

public class AttemptToBindDisallowedFieldException extends RuntimeException{

    public AttemptToBindDisallowedFieldException(String message) {
        super(message);
    }
}
