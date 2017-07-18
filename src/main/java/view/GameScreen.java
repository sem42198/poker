package view;

import javafx.scene.layout.BorderPane;
import viewcontroller.PokerGameController;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class GameScreen extends BorderPane {

    private PlayerArea left;
    private PlayerArea top;
    private PlayerArea right;
    private PlayerArea bottom;
    private BoardArea center;

    /**
     * GameScreen's constructor
     * @param controller The PokerGameController to interact with
     */
    public GameScreen(PokerGameController controller) {
        left = new VerticalPlayer(controller.getLeftPlayer());
        top = new HorizontalPlayer(controller.getTopPlayer());
        right = new VerticalPlayer(controller.getRightPlayer());
        bottom = new HorizontalPlayer(controller.getBottomPlayer());
        center = new BoardArea(controller.getBoard());
        setLeft(left.playerPane());
        setTop(top.playerPane());
        setRight(right.playerPane());
        setBottom(bottom.playerPane());
        setCenter(center.getPane());
    }

    /**
     * This method is called whenever normal updates to the UI need to be made.
     */
    public void updatesMade() {
        left.update(false);
        top.update(false);
        right.update(false);
        bottom.update(true);
        center.update();
    }

    /**
     * This method is called whenever a round of poker ends
     */
    public void endOfRound() {
        left.update(true);
        top.update(true);
        right.update(true);
        right.update(true);
        center.update();
    }
}