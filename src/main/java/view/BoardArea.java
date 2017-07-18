package view;

import javafx.scene.layout.HBox;
import model.Board;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class BoardArea {

    private HBox pane;

    private Board board;
    private CardView[] cards;
    private Label pot;


    /**
     * Constructor for the board's display
     * @param  board The Board object that contains data associated with the
     * board
     */
    public BoardArea(Board board) {
        this.board = board;
        cards = new CardView[5];
        for (int i = 0; i < 5; i++) {
            cards[i] = new CardView();
        }
        pot = new Label("Pot: " + board.getPot());
        pot.setPrefWidth(50);
        pane = new HBox(20, cards);
        pane.getChildren().add(pot);
        pane.setAlignment(Pos.CENTER);
    }

    /**
     * Getter for the HBox that all UI elements are on
     * @return the HBox that all Board UI elements are on
     */
    public HBox getPane() {
        return pane;
    }

    /**
     * Updates UI elements
     */
    public void update() {
        for (int i = 0; i < 5; i++) {
            if (i < board.getNumCards()) {
                cards[i].setCard(board.getTableCard(i));
                cards[i].show();
            } else {
                cards[i].hide();
            }
        }
        pot.setText("Pot: " + board.getPot());
    }
}