package objects.fill.annotation_processor.exceptions;

/**
 * Custom exception class for errors related to generating random values.
 */
public class RandomValueException extends RuntimeException {

    /**
     * Constructs a RandomValueException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public RandomValueException(Throwable cause) {
        super("Can't create instance of class", cause);
    }

    /**
     * Constructs a RandomValueException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    @SuppressWarnings("unused")
    public RandomValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
