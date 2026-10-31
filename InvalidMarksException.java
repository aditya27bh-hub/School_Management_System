// This is our own exception.
// Exception is already made by Java, we just extend it.
public class InvalidMarksException extends Exception {

    public InvalidMarksException(String message) {
        super(message);
    }
}
