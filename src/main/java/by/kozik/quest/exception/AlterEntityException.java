package by.kozik.quest.exception;

/**
 * Created by Serge on 09.01.2017.
 */
public class AlterEntityException extends Exception  {

    public AlterEntityException() {
    }

    public AlterEntityException(String message) {
        super(message);
    }

    public AlterEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlterEntityException(Throwable cause) {
        super(cause);
    }
}