package objects.fill.annotation_processor.exceptions;

public class RandomValueException extends RuntimeException {

    public RandomValueException(Throwable cause) {
        super("Can't create instance of class", cause);
    }

    @SuppressWarnings("unused")
    public RandomValueException(String message, Throwable cause) {
        super(message, cause);
    }

}
