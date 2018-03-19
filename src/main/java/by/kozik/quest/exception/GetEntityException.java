package by.kozik.quest.exception;

/**
 * Created by Serge on 14.01.2017.
 */
public class GetEntityException extends Exception  {

    public GetEntityException() {
    }

    public GetEntityException(String message) {
        super(message);
    }

    public GetEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetEntityException(Throwable cause) {
        super(cause);
    }
}