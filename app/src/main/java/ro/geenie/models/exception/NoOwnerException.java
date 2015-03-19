package ro.geenie.models.exception;

/**
 * Created by motan on 28.02.2015.
 */
public class NoOwnerException extends Exception {

    public NoOwnerException() {
    }

    public NoOwnerException(String detailMessage) {
        super(detailMessage);
    }
}
