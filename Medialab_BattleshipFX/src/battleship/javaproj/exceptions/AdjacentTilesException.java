package battleship.javaproj.exceptions;
import java.lang.*;

public class AdjacentTilesException extends RuntimeException{
    public static String message = "ship is adjacent to another ship.";
    public AdjacentTilesException() {
        super(message);
    }
}
