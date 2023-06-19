package be.lennertsoffers.spigotbootstrapper.core.exception;

public class InitializationException extends RuntimeException {

    public InitializationException(Throwable cause) {
        super(cause);
    }

    public InitializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
