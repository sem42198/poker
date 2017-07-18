package view;

import javafx.scene.layout.Pane;

import model.Player;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public abstract class PlayerArea {

    private Pane pane;

    private Player player;

    private CardView card1;
    private CardView card2;
    private Label name;
    private Label money;
    private Label isOut;
    private VBox info;

    /**
     * PlayerArea's constructor
     * @param  pane   The Pane where all UI elements will be added. The type of
     * pane is decided by subclasses
     * @param  player The Player who's information will be tracked
     */
    public PlayerArea(Pane pane, Player player) {
        this.pane = pane;
        this.player = player;
        card1 = new CardView();
        card2 = new CardView();
        name = new Label(player.toString());
        money = new Label("Chips: " + player.getMoney());
        isOut = new Label("Out of play");
        info = new VBox(name, money);
        pane.getChildren().addAll(card1, card2, info);
        if (player.getOutOfPlay()) {
            info.getChildren().add(isOut);
        }
        info.setPrefHeight(60);
        info.setPrefWidth(100);
        info.setAlignment(Pos.TOP_CENTER);
    }

    /**
     * Getter for the Pane that contains all of the UI elements.
     * @return The Pane that contains all of the UI elements.
     */
    public Pane playerPane() {
        return pane;
    }

    /**
     * This method is called whenever an update to the UI needs to be made.
     * @param showDetails is true whenever the details of the front of the
     * cards are supposed to be shown false otherwise
     */
    public void update(boolean showDetails) {
        if (player.getCard(0) != null) {
            card1.setCard(player.getCard(0));
        }
        if (player.getCard(1) != null) {
            card2.setCard(player.getCard(1));
        }
        money.setText("Chips: " + player.getMoney());
        if (player.getOutOfPlay()) {
            if (!info.getChildren().contains(isOut)) {
                info.getChildren().add(isOut);
            }
            card1.hide();
            card2.hide();
        } else if (!showDetails) {
            card1.hideDetails();
            card2.hideDetails();
            if (info.getChildren().contains(isOut)) {
                info.getChildren().remove(isOut);
            }
        } else if (showDetails) {
            card1.show();
            card2.show();
            if (info.getChildren().contains(isOut)) {
                info.getChildren().remove(isOut);
            }
        }
    }
}