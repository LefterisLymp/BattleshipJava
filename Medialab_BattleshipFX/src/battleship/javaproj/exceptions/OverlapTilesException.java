package battleship.javaproj.exceptions;
import java.lang.*;

public class OverlapTilesException extends RuntimeException{
    public static String message = "ship overlaps another ship.";
    public OverlapTilesException() {
        super(message);
    }
}