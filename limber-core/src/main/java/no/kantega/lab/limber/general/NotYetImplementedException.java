package no.kantega.lab.limber.general;

public class NotYetImplementedException extends RuntimeException {

    public NotYetImplementedException() {
    }

    public NotYetImplementedException(String message) {
        super(message);
    }

    public NotYetImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotYetImplementedException(Throwable cause) {
        super(cause);
    }
}
