package objects.fill.annotation_processor.exceptions;

/**
 * Custom exception class for Fill-related errors.
 */
public class FillException extends RuntimeException {

    /**
     * Constructs a FillException with the specified detail message.
     *
     * @param message the detail message
     */
    public FillException(String message) {
        super(message);
    }
}