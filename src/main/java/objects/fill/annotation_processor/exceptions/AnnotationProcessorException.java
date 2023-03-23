package objects.fill.annotation_processor.exceptions;

public class AnnotationProcessorException extends RuntimeException {

    public AnnotationProcessorException() {
        super("Can't find processor in list");
    }

    @SuppressWarnings("unused")
    public AnnotationProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

}
