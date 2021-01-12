package sample;

import battleship.javaproj.Board;
import battleship.javaproj.Ship;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Cell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BoardFX extends Parent {
    private int gsize;
    private VBox rows = new VBox();
    private Cell[] cells = new Cell[100];

    public BoardFX(int gsize) {
        this.gsize = gsize;
        HBox starting = new HBox();
        starting.setSpacing(20);
        Text init = new Text("");
        starting.getChildren().add(init);
        for (int i = 0; i < gsize; i++) {
            Text number = new Text(String.valueOf(i));
            number.setFont(Font.font("candela", 20));
            number.setFill(Color.LIGHTYELLOW);
            starting.getChildren().add(number);
        }

        rows.getChildren().add(starting);

        for (int y = 0; y < gsize; y++) {
            HBox row = new HBox();
            Text number = new Text(String.valueOf(y));
            number.setFont(Font.font("candela", 20));
            number.setFill(Color.LIGHTYELLOW);
            row.getChildren().add(number);
            for (int x = 0; x < gsize; x++) {
                Cell c = new Cell();
                row.getChildren().add(c);
                cells[gsize*y+x] = c;
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    public void setBoard(Board board) {
        for (int i = 0; i < board.getGridSize(); i++) {
            for (int j = 0; j < board.getGridSize(); j++) {
                int index = i*gsize + j;
                int value = board.at(index);
                switch(value) {
                    case 1: cells[index].setColor(Color.TEAL); break;
                    case 2: cells[index].setColor(Color.GRAY); break;
                    case 3: cells[index].setColor(Color.ORANGE); break;
                    case 4: cells[index].setColor(Color.YELLOW); break;
                    case 5: cells[index].setColor(Color.LIGHTGREEN); break;
                    case 6: cells[index].setColor(Color.WHITE); break;
                    case 7: cells[index].setColor(Color.RED); break;
                    default: cells[index].setColor(Color.BLACK); break;
                }
            }
        }
    }

    public class Cell extends Rectangle {

        private Board board;

        public Cell() {
            super(30, 30);
            setFill(Color.BLACK);
            setStroke(Color.SLATEGREY);
        }

        public void setColor(Color c) {
            setFill(c);
        }
    }
}
