package objects.fill.annotation_processor.exceptions;

/**
 * Exception class for Annotation Processor errors.
 */
public class AnnotationProcessorException extends RuntimeException {

    /**
     * Constructs an AnnotationProcessorException with a default message.
     */
    public AnnotationProcessorException() {
        super("Can't find processor in list");
    }

    /**
     * Constructs an AnnotationProcessorException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    @SuppressWarnings("unused")
    public AnnotationProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
