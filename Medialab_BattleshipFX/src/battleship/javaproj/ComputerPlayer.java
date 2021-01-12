package battleship.javaproj;

import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer {
    private ArrayList<Integer> available_shots;
    private ArrayList<Integer> successful_shots;
    private int gsize;

    public ComputerPlayer (int grid_size) {
        gsize = grid_size;
        available_shots = new ArrayList<Integer>();
        successful_shots = new ArrayList<Integer>();
        int i;
        int g = grid_size*grid_size;
        for (i = 0; i < g; i++) {
            available_shots.add(i);
        }
    }

    public int play() {
        if (successful_shots.isEmpty()) {
            Random rn = new Random();
            int dummy = rn.nextInt() % available_shots.size();
            if (dummy < 0) dummy = (-1)*dummy;
            Integer next_move = available_shots.get(dummy);
            available_shots.remove(next_move);
            return next_move;
        }
        else if (successful_shots.size() == 1) {
            int current_shot = successful_shots.get(0);
            ArrayList<Integer> picker = new ArrayList<Integer>();
            if (in_Bounds(current_shot-1, current_shot/gsize) && available_shots.contains(current_shot - 1)) {picker.add(current_shot-1);} //Left
            if (in_Bounds(current_shot+1, current_shot/gsize) && available_shots.contains(current_shot + 1)) {picker.add(current_shot+1);} //Right
            if (in_Bounds(current_shot-gsize, current_shot/gsize - 1) && available_shots.contains(current_shot - gsize)) {picker.add(current_shot-gsize);} //Up
            if (in_Bounds(current_shot+gsize, current_shot/gsize + 1) && available_shots.contains(current_shot + gsize)) {picker.add(current_shot+gsize);} //Down
            Random rn = new Random();
            Integer next_move = picker.get(Math.abs(rn.nextInt() % picker.size()));
            available_shots.remove(next_move);
            return next_move;
        }
        else {
            int i, minim = gsize*gsize, maxim = -1;
            for (i = 0; i < successful_shots.size(); i++) {
                if (successful_shots.get(i) < minim) minim = successful_shots.get(i);
                if (successful_shots.get(i) > maxim) maxim = successful_shots.get(i);
            }
            ArrayList<Integer> picker = new ArrayList<Integer>();
            boolean horizontal = ShipIsHorizontal();
            if (horizontal) { //Horizontal ship case
                if (in_Bounds(minim-1, minim/gsize) && available_shots.contains(minim-1)) {picker.add(minim-1);}
                if (in_Bounds(maxim+1, maxim/gsize) && available_shots.contains(maxim+1)) {picker.add(maxim+1);}
            }
            else { //Vertical ship case
                if (in_Bounds(minim-gsize, minim/gsize - 1) && available_shots.contains(minim-gsize)) {picker.add(minim-gsize);}
                if (in_Bounds(maxim+gsize, maxim/gsize + 1) && available_shots.contains(maxim+gsize)) {picker.add(maxim+gsize);}
            }
            Random rn = new Random();
            Integer next_move = picker.get(Math.abs(rn.nextInt() % picker.size()));
            available_shots.remove(next_move);
            return next_move;
        }
    }

    private boolean ShipIsHorizontal() {
        if (Math.abs(successful_shots.get(0) - successful_shots.get(1)) == 1) return true;
        return false;
    }

    public void UpdateOnSuccessful(int shot) {
        successful_shots.add(shot);
    }

    public void UpdateOnSunken(int shot) {
        successful_shots.add(shot);
        int i;
        for (i = 0; i < successful_shots.size(); i++) {
            Integer spot = successful_shots.get(i) - 1;
            available_shots.remove(spot);
            spot = successful_shots.get(i) + 1;
            available_shots.remove(spot);
            spot = successful_shots.get(i) + gsize;
            available_shots.remove(spot);
            spot = successful_shots.get(i) - gsize;
            available_shots.remove(spot);
        }
        successful_shots.clear();
    }

    private boolean in_Bounds(int index, int row) {
        return !((index/gsize != row) || (index < 0) || (index >= gsize*gsize));
    }
}