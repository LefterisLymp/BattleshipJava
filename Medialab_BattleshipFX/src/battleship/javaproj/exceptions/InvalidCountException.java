package battleship.javaproj.exceptions;
import java.lang.*;

public class InvalidCountException extends RuntimeException{
    public static String message = "ship type has more than one instance.";
    public InvalidCountException() {
        super(message);
    }
}