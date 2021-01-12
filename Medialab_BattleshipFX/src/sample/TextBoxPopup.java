package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TextBoxPopup {
    public static String player_scenario;
    public static String enemy_scenario;

    public TextBoxPopup() {
        player_scenario = null;
        enemy_scenario = null;
    }

    public static void get() {
        final Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Enter scenarios");
        window.setMinWidth(250);

        Label label1 = new Label();
        label1.setText("Player scenario:");
        final TextField txtfield1 = new TextField();
        txtfield1.setPromptText("Set player file path");
        txtfield1.setMaxWidth(200);

        txtfield1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TextBoxPopup.player_scenario = txtfield1.getText();
                if (TextBoxPopup.player_scenario != null && TextBoxPopup.enemy_scenario != null) window.close();
            }
        });

        Label label2 = new Label();
        label2.setText("Enemy scenario:");

        final TextField txtfield2 = new TextField();
        txtfield2.setPromptText("Set computer file path");
        txtfield2.setMaxWidth(200);

        txtfield2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TextBoxPopup.enemy_scenario = txtfield2.getText();
                if (TextBoxPopup.player_scenario != null && TextBoxPopup.enemy_scenario != null) window.close();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, txtfield1, label2, txtfield2);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}