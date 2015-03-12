package ro.geenie.models.exception;

/**
 * Created by motan on 28.02.2015.
 */
public class DbNotAuthorisedException extends Exception {
    public DbNotAuthorisedException(String detailMessage) {
        super(detailMessage);
    }
}
