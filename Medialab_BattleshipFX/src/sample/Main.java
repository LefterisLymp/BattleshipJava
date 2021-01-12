package sample;

import battleship.javaproj.BattleshipJava;
import battleship.javaproj.Ship;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.lang.Math;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class Main extends Application {

    private BattleshipJava battleship = null;
    private BattleshipJava loaded_game = null;
    private boolean game_loaded = false;
    private boolean game_started = false;
    private boolean shot_registered = false;
    private BoardFX player_board;
    private BoardFX computer_board;
    private int player_move_x = -1;
    private int player_move_y = -1;
    private int turn;
    private int current_turn;
    Text moves_text = new Text();
    Text player_ships_text = new Text();
    Text computer_ships_text = new Text();
    Text player_points_text = new Text();
    Text computer_points_text = new Text();
    Text player_rate_text = new Text();
    Text computer_rate_text = new Text();

    @Override
    public void start(Stage primaryStage) throws Exception{

        MenuBar menubar = new MenuBar();
        menubar.prefWidthProperty().bind(primaryStage.widthProperty());

        Menu app_menu = new Menu("Application");
        Menu det_menu = new Menu("Details");
        menubar.getMenus().addAll(app_menu, det_menu);

        MenuItem m1 = new MenuItem("Start");
        MenuItem m2 = new MenuItem("Load");
        MenuItem m3 = new MenuItem("Exit");
        app_menu.getItems().addAll(m1, m2, m3);

        MenuItem m4 = new MenuItem("Enemy Ships");
        MenuItem m5 = new MenuItem("Player Shots");
        MenuItem m6 = new MenuItem("Enemy Shots");
        det_menu.getItems().addAll(m4, m5, m6);

        moves_text.setFont(Font.font("candela", 20));
        player_ships_text.setFont(Font.font("candela", 20));
        computer_ships_text.setFont(Font.font("candela", 20));
        player_points_text.setFont(Font.font("candela", 20));
        computer_points_text.setFont(Font.font("candela", 20));
        player_rate_text.setFont(Font.font("candela", 20));
        computer_rate_text.setFont(Font.font("candela", 20));

        moves_text.setFill(Color.LIGHTYELLOW);
        player_ships_text.setFill(Color.LIGHTYELLOW);
        computer_ships_text.setFill(Color.LIGHTYELLOW);
        player_points_text.setFill(Color.LIGHTYELLOW);
        computer_points_text.setFill(Color.LIGHTYELLOW);
        player_rate_text.setFill(Color.LIGHTYELLOW);
        computer_rate_text.setFill(Color.LIGHTYELLOW);

        final TextField txtfield1 = new TextField();
        txtfield1.setPromptText("Set row");

        txtfield1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                player_move_x = parseInt(txtfield1.getText());
            }
        });

        final TextField txtfield2 = new TextField();
        txtfield2.setPromptText("Set column");

        txtfield2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                player_move_y = parseInt(txtfield2.getText());
            }
        });

        Button fire_button = new Button("Fire shot!");

        GridPane gpane = new GridPane();
        gpane.setStyle("-fx-background-color: #FFFDD0;");
        gpane.setAlignment(Pos.BOTTOM_LEFT);
        gpane.add(txtfield1, 0, 0);
        gpane.add(txtfield2, 1, 0);
        gpane.add(fire_button, 2, 0);

        player_board = new BoardFX(10);
        computer_board = new BoardFX(10);

        HBox board_box = new HBox();
        board_box.getChildren().addAll(player_board, computer_board);
        board_box.setSpacing(100);
        board_box.setPadding(new Insets(100, 0, 100, 100));

        HBox texts = new HBox();
        texts.getChildren().addAll(moves_text, player_ships_text, computer_ships_text, player_points_text, computer_points_text, player_rate_text, computer_rate_text);
        texts.setSpacing(20);

        AnchorPane root = new AnchorPane();
        GridPane gridroot = new GridPane();
        root.getStylesheets().add("sample/style.css");
        root.getStyleClass().add("bodybg");
        primaryStage.setTitle("Medialab Battleship");
        root.setPadding(new Insets(0, 0, 0, 0));
        gridroot.add(menubar, 0, 0);
        gridroot.add(texts, 0, 1);
        gridroot.add(board_box, 0, 2);

        BorderPane brdpane = new BorderPane();
        root.getChildren().addAll(gridroot, brdpane);
        root.setBottomAnchor(brdpane, 0.0);
        brdpane.setBottom(gpane);

        root.minWidthProperty().bind(primaryStage.widthProperty());
        root.minHeightProperty().bind(primaryStage.heightProperty());

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        txtfield1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                player_move_x = parseInt(txtfield1.getText());
            }
        });

        txtfield2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                player_move_y = parseInt(txtfield2.getText());
            }
        });


        fire_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent a) {fireShotandContinueGame();}});

        m1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {StartButtonHandler();}});

        m2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {LoadButtonHandler();}});

        m3.setOnAction (new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        m4.setOnAction (new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {EnemyShipsHandler();}});

        m5.setOnAction (new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {PlayerMovesHandler();}});

        m6.setOnAction (new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {EnemyMovesHandler();}});
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void TextUpdate(Text... args) {
        args[0].setText("Moves left:\n" + String.valueOf(battleship.total_moves)); //Moves left
        args[1].setText("Player remaining ships:\n" + String.valueOf(battleship.getPlayer_remaining_ships())); //Player ships
        args[2].setText("Computer remaining ships:\n" + String.valueOf(battleship.getComputer_remaining_ships())); //Computer ships
        args[3].setText("Player points:\n" + String.valueOf(battleship.getPlayer_points())); //Player points
        args[4].setText("CPU points:\n" + String.valueOf(battleship.getComputer_points())); //Computer points
        String message1, message2;
        if (battleship.getPlayer_shots() == 0) message1 = "N/A";
        else message1 = String.valueOf((int)Math.round(100*battleship.getPlayer_successful_shots()*1.0/battleship.getPlayer_shots())) + "%";
        if (battleship.getComputer_shots() == 0) message2 = "N/A";
        else message2 = String.valueOf((int)Math.round(100*battleship.getComputer_successful_shots()*1.0/battleship.getComputer_shots())) + "%";
        args[5].setText("Player shots success rate\n" + message1); //Player shot %
        args[6].setText("Computer shots success rate\n" + message2); //Computer shot %
    }

    private void fireShotandContinueGame() {
        if (!game_started) {
            AlertBox e = new AlertBox();
            e.display("No started game!", "Please start a game.");
            return;
        }
        int i = 0;
        while (battleship.total_moves > 0 && battleship.getPlayer_remaining_ships() > 0 && battleship.getComputer_remaining_ships() > 0 && i < 2) {
            i++;

            if (current_turn == 0) {
                int shot = player_move_x * battleship.getGrid_size() + player_move_y;
                //Checking if the player has already made the shot
                if (player_move_x < 0 || player_move_x >= battleship.getGrid_size() || player_move_y < 0 || player_move_y >= battleship.getGrid_size()) {
                    AlertBox e = new AlertBox();
                    e.display("Shot out of bounds!", "Please try another shot.");
                    return;
                }
                if (battleship.getOpponent_board().at(shot) > battleship.getComputer_fleet().length) {
                    AlertBox e = new AlertBox();
                    e.display("Shot already made!", "This shot is already made.");
                    return;
                }
                battleship.insert_player_shot(shot);
                computer_board.setBoard(battleship.getOpponent_board());
            }
            if (current_turn == 1) {
                battleship.insertComputerShot();
                player_board.setBoard(battleship.getPlayerBoard());
            }
            current_turn = (current_turn == 1) ? 0 : 1;
            if (current_turn == turn) battleship.total_moves--;
            TextUpdate(moves_text, player_ships_text, computer_ships_text, player_points_text, computer_points_text, player_rate_text, computer_rate_text);
        }

        if (battleship.total_moves > 0 && battleship.getPlayer_remaining_ships() > 0 && battleship.getComputer_remaining_ships() > 0) return;
        AlertBox e = new AlertBox();
        String message;
        if (battleship.getPlayer_remaining_ships() == 0) message = "Player's ships are sunken. CPU wins!";
        else if (battleship.getComputer_remaining_ships() == 0) message = "CPU's ships are sunken. You win!";
        else {
            if (battleship.getPlayer_points() > battleship.getComputer_points()) message = "You win!";
            else if (battleship.getComputer_points() > battleship.getPlayer_points()) message = "CPU wins!";
            else message = "It's a draw.";
        }
        e.display("Result", message);
        game_started = false;
    }

    private void StartButtonHandler() {
        if (!game_loaded) {
            AlertBox alr = new AlertBox();
            alr.display("No loaded games!", "Please load a game.");
            return;
        }
        if (game_started) {
            battleship = new BattleshipJava();
        }
        game_started = true;
        battleship = loaded_game;
        game_loaded = false;
        player_board.setBoard(battleship.getPlayerBoard());
        computer_board.setBoard(battleship.getOpponent_board());
        Random rn = new Random();
        turn = Math.abs(rn.nextInt() % 2);
        current_turn = turn;
        TextUpdate(moves_text, player_ships_text, computer_ships_text, player_points_text, computer_points_text, player_rate_text, computer_rate_text);
        if (turn == 0) {
            AlertBox e = new AlertBox();
            e.display("", "You play first.");
        }
        else {
            AlertBox e = new AlertBox();
            e.display("", "Computer plays first.");
            battleship.insertComputerShot();
            player_board.setBoard(battleship.getPlayerBoard());
            TextUpdate(moves_text, player_ships_text, computer_ships_text, player_points_text, computer_points_text, player_rate_text, computer_rate_text);
            current_turn = 0;
        }
    }

    private void LoadButtonHandler() {
        boolean valid_files = true;
        TextBoxPopup txtbox = new TextBoxPopup();
        txtbox.get();
        BattleshipJava new_game = new BattleshipJava();
        try {
            new_game.setPlayerShips(TextBoxPopup.player_scenario);
        }
        catch(FileNotFoundException e) {
            valid_files = false;
            AlertBox alert = new AlertBox();
            alert.display("Check your files", "Player's file not found.");
        }
        catch (IOException e) {
            valid_files = false;
            AlertBox alert = new AlertBox();
            alert.display("Invalid format", "Player's file has invalid format.");
        }
        catch (RuntimeException e) {
            valid_files = false;
            AlertBox alert = new AlertBox();
            alert.display("Check your ship configuration", "Player: " + e.getMessage());
        }
        try {
            new_game.setComputerShips(TextBoxPopup.enemy_scenario);
        }
        catch(FileNotFoundException e) {
            valid_files = false;
            AlertBox alert = new AlertBox();
            alert.display("Check your files", "CPU's file not found.");
        }
        catch (IOException e) {
            valid_files = false;
            AlertBox alert = new AlertBox();
            alert.display("Invalid format", "CPU's file has invalid format.");
        }
        catch (RuntimeException e) {
            valid_files = false;
            AlertBox alert = new AlertBox();
            alert.display("Check your ship configuration", "CPU: " + e.getMessage());
        }
        if (valid_files) {
            game_loaded = true;
            loaded_game = new_game;
            AlertBox alert = new AlertBox();
            alert.display("Success!", "Loaded successfully!");
        }
    }

    private void EnemyShipsHandler() {
        if (!game_started) {
            AlertBox e = new AlertBox();
            e.display("Start a game", "No game started!");
            return;
        }
        StringBuilder txt = new StringBuilder();
        for (Ship s:battleship.getComputer_fleet()) {
            String condition;
            if (!s.isHit() && (!s.isSunken())) condition = "Intact";
            else if (s.isHit()) condition = "Hit";
            else condition = "Sunken";
            txt.append(s.returnType() + ", " + condition + "\n");
        }
        AlertBox e = new AlertBox();
        e.display("Enemy ships", txt.toString());
    }

    private void PlayerMovesHandler() {
        if (!game_started) {
            AlertBox e = new AlertBox();
            e.display("Start a game", "No game started!");
            return;
        }
        AlertBox e = new AlertBox();
        e.display("Player last 5 shots", battleship.getPlayerShots());
    }

    private void EnemyMovesHandler() {
        if (!game_started) {
            AlertBox e = new AlertBox();
            e.display("Start a game", "No game started!");
            return;
        }
        AlertBox e = new AlertBox();
        e.display("Computer last 5 shots", battleship.getComputerShots());
    }
}