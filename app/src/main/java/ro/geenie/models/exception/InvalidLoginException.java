package ro.geenie.models.exception;

/**
 * Created by motan on 28.02.2015.
 */
public class InvalidLoginException extends Exception{
    public InvalidLoginException(String detailMessage) {
        super(detailMessage);
    }
}
