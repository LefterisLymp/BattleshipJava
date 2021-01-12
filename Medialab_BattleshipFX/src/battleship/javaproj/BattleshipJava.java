package battleship.javaproj;

import battleship.javaproj.exceptions.AdjacentTilesException;
import battleship.javaproj.exceptions.InvalidCountException;
import battleship.javaproj.exceptions.OverlapTilesException;
import battleship.javaproj.exceptions.OversizeException;
import battleship.javaproj.ships.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class BattleshipJava {
    public int total_moves;
    private int grid_size;
    private Board player_board;
    private Board opponent_board;
    private int player_remaining_ships;
    private int computer_remaining_ships;
    private Ship[] player_fleet = {new Carrier(), new Battleship(), new Cruiser(), new Submarine(), new Destroyer()};
    private Ship[] computer_fleet = {new Carrier(), new Battleship(), new Cruiser(), new Submarine(), new Destroyer()};
    private RegistryQueue player_last5 = new RegistryQueue(5);
    private RegistryQueue computer_last5 = new RegistryQueue(5);
    private int player_points = 0;
    private int computer_points = 0;
    private int player_shots = 0;
    private int computer_shots = 0;
    private int player_successful_shots = 0;
    private int computer_successful_shots = 0;

    private ComputerPlayer cpu;

    public BattleshipJava() {
        total_moves = 40;
        grid_size = 10;
        player_board = new Board(grid_size, player_fleet.length);
        opponent_board = new Board(grid_size, computer_fleet.length);
        cpu = new ComputerPlayer(grid_size);
        player_remaining_ships = player_fleet.length;
        computer_remaining_ships = computer_fleet.length;
    }

    private void placeShipsFromFiles (BufferedReader file1, Ship[] fleet, Board brd) throws OversizeException, OverlapTilesException, AdjacentTilesException, InvalidCountException, IOException {
        String s;
        HashSet<Integer> ship_set = new HashSet<Integer>();
        while ((s = file1.readLine()) != null) {
            String[] numbers = s.split(",");
            int i1 = Integer.parseInt(numbers[0]);
            int i2 = Integer.parseInt(numbers[1]);
            int i3 = Integer.parseInt(numbers[2]);
            int i4 = Integer.parseInt(numbers[3]);

            if (ship_set.contains(i1)) throw new InvalidCountException();
            else ship_set.add(i1);

            Ship current_ship = fleet[i1-1];
            int[] ship_spots = new int[current_ship.getLength()];
            int starting_position = i2*grid_size + i3;

            int ship_step = (i4 == 1) ? 1 : grid_size;
            int row_step = (i4 == 1) ? 0 : 1;
            int row = starting_position/grid_size;
            int iter = 0;
            for (; iter < current_ship.getLength(); iter++, starting_position += ship_step, row += row_step) {
                if (!in_Bounds(starting_position, row)) throw new OversizeException();
                if (brd.at(starting_position) != 0) throw new OverlapTilesException();
                if (in_Bounds(starting_position + 1, row) && (brd.at(starting_position + 1) != 0 && brd.at(starting_position + 1) != i1))
                    throw new AdjacentTilesException();
                if (in_Bounds(starting_position - 1, row) && (brd.at(starting_position - 1) != 0 && brd.at(starting_position - 1) != i1))
                    throw new AdjacentTilesException();
                if (in_Bounds(starting_position + grid_size, row+1) && (brd.at(starting_position + grid_size) != 0 && brd.at(starting_position + grid_size) != i1))
                    throw new AdjacentTilesException();
                if (in_Bounds(starting_position - grid_size, row-1) && (brd.at(starting_position - grid_size) != 0 && brd.at(starting_position - grid_size) != i1))
                    throw new AdjacentTilesException();
                brd.set(starting_position, i1);
                ship_spots[iter] = starting_position;
            }
            current_ship.setSpaces(ship_spots);
        }
    }


    private boolean in_Bounds(int index, int row) {
        return !((index/grid_size != row) || (index < 0) || (index >= grid_size*grid_size));
    }

    public void setPlayerShips(String args) throws FileNotFoundException, IOException, RuntimeException {
        BufferedReader fis1;
        fis1 = new BufferedReader(new FileReader(args));
        placeShipsFromFiles(fis1, player_fleet, player_board);
    }

    public void setComputerShips(String args) throws FileNotFoundException, IOException, RuntimeException {
        BufferedReader fis1;
        fis1 = new BufferedReader(new FileReader(args));
        placeShipsFromFiles(fis1, computer_fleet, opponent_board);
        int i = 0;
        for (; i < grid_size*grid_size; i++) opponent_board.set(i, 0);
    }

    public int getGrid_size() {return grid_size;}

    public Board getPlayerBoard() {return player_board;}

    public ComputerPlayer getComputerPlayer() {return cpu;}

    public int getPlayer_remaining_ships() {return player_remaining_ships;}

    public int getComputer_remaining_ships() {return computer_remaining_ships;}

    public Board getOpponent_board() { return opponent_board;}

    public Ship[] getComputer_fleet() {return computer_fleet;}

    public String getPlayerShots() {
        return player_last5.getAll(true);
    }

    public int getPlayer_points() {return player_points;}

    public int getComputer_points() {return computer_points;}

    public int getComputer_shots() {return computer_shots;}

    public int getComputer_successful_shots() {return computer_successful_shots;}

    public int getPlayer_successful_shots() {return player_successful_shots;}

    public int getPlayer_shots() {return player_shots;}

    public String getComputerShots() {
        return computer_last5.getAll(true);
    }

    public void insert_player_shot(int shot) {
        player_shots++;
        Ship ship = null;
        String attempt = "(" + String.valueOf(shot / grid_size) + "," + String.valueOf(shot % grid_size) + ")";
        while (true) {
            for (Ship s : computer_fleet) {
                if (s.successfulShot(shot)) {
                    ship = s;
                    opponent_board.set(shot, computer_fleet.length + 2); //Red set
                    break;
                }
            }
            if (ship != null) break;
            player_last5.push(attempt + ", Failed");
            opponent_board.set(shot, computer_fleet.length + 1); //White set
            break;
        }
        if (ship != null) {
            player_successful_shots++;
            if (ship.isSunken()) {
                player_points += ship.givePoints() + ship.giveBonusPoints();
                computer_remaining_ships--;
            } else {
                player_points += ship.givePoints();
            }
            player_last5.push(attempt + ", Successful, " + ship.returnType());
        }
    }

    public void insertComputerShot () {
        computer_shots++;
        Ship ship = null;
        int shot = cpu.play();
        String attempt = "(" + String.valueOf(shot / grid_size) + "," + String.valueOf(shot % grid_size) + ")";

        while (true) {
            for (Ship s : player_fleet) {
                if (s.successfulShot(shot)) {
                    ship = s;
                    player_board.set(shot, computer_fleet.length + 2); //Red set
                    break;
                }
            }
            if (ship != null) break;
            computer_last5.push(attempt + ", Failed");
            player_board.set(shot, computer_fleet.length+1); //White set
            break;
        }
        if (ship != null) {
            computer_successful_shots++;
            if (ship.isSunken()) {
                cpu.UpdateOnSunken(shot);
                computer_points += ship.givePoints() + ship.giveBonusPoints();
                player_remaining_ships--;
            }
            else {
                cpu.UpdateOnSuccessful(shot);
                computer_points += ship.givePoints();
            }
            computer_last5.push(attempt + ", Successful, " + ship.returnType());
        }
    }

}