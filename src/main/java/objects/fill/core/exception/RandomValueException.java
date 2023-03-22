package objects.fill.core.exception;

public class RandomValueException extends RuntimeException {

    public RandomValueException(Throwable cause) {
        super("Can't create instance of class", cause);
    }

    public RandomValueException(String message, Throwable cause) {
        super(message, cause);
    }

}
