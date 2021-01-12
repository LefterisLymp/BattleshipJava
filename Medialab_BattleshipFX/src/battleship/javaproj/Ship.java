package battleship.javaproj;

import java.util.*;

public class Ship {
    protected String name;
    protected int length;
    protected int remaining_spots;
    protected int points;
    protected int bonus_points;
    protected HashSet<Integer> spaces;

    public Ship (String s, int l, int p, int bp) {
        name = s;
        length = l;
        remaining_spots = l;
        points = p;
        bonus_points = bp;
        spaces = new HashSet<Integer>();
    }

    public void setSpaces(int[] arr) {
        int i = 0;
        for (i = 0; i < length; i++) {
            spaces.add(arr[i]);
        }
    }

    public boolean successfulShot(int shot) {
        if (spaces.contains(shot)) {
            spaces.remove(shot);
            remaining_spots--;
            return true;
        }
        return false;
    }

    public int getLength() {return length;}

    public String returnType() {return name;}

    public int givePoints() {return points;}

    public boolean isSunken() {return (remaining_spots == 0);}

    public int giveBonusPoints() {return bonus_points;}

    public boolean isHit() {return ((remaining_spots < length) && (remaining_spots > 0));}
}
