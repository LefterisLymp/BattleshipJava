package battleship.javaproj.exceptions;
import java.lang.*;

public class OversizeException extends RuntimeException{
    public static String message = "ship is out of bounds.";
    public OversizeException() {
        super(message);
    }
}