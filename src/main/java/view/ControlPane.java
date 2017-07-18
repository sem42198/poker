package view;

import javafx.scene.layout.HBox;
import viewcontroller.PokerGameController;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class ControlPane extends HBox {

    private PokerGameController cont;

    private TextField amount;
    private Button raise;
    private Button call;
    private Button fold;
    private Button newRound;
    private Button saveGame;

    /**
     * Constructor for ControlPane
     * @param  cont The PokerGameController to interact with
     */
    public ControlPane(PokerGameController cont) {
        this.cont = cont;
        setSpacing(20);
        amount = new TextField();
        raise = new Button("Raise");
        raise.setOnAction(e -> {
                try {
                    int chips  = Integer.parseInt(amount.getText());
                    if (cont.humanBet(chips)) {
                        disableButtons();
                    }
                } catch (NumberFormatException exc1) {
                    Console.putMessage("Please enter an integer value.");
                } catch (IllegalArgumentException exc2) {
                    Console.putMessage(exc2.getMessage());
                } finally {
                    amount.clear();
                }
            });
        call = new Button("Call");
        call.setOnAction(e -> {
                cont.humanCall();
                disableButtons();
            });
        fold = new Button("Fold");
        fold.setOnAction(e -> {
                cont.humanFold();
                disableButtons();
            });
        newRound = new Button("Start new round");
        newRound.setOnAction(e -> {
                cont.startNewPokerHand();
                getChildren().remove(newRound);
                getChildren().remove(saveGame);
                disableButtons();
            });
        saveGame = new Button("Save game");
        saveGame.setOnAction(e -> {
                if (saveGame()) {
                    getChildren().remove(saveGame);
                }
            });
        getChildren().addAll(amount, raise, call, fold);
        setAlignment(Pos.CENTER);
    }

    /**
     * disables the control pane's buttons and text field
     */
    public void disableButtons() {
        amount.setDisable(true);
        raise.setDisable(true);
        call.setDisable(true);
        fold.setDisable(true);
    }

    /**
     * Called whenever it becomes the player's turn again
     */
    public void playerTurn() {
        amount.setDisable(false);
        raise.setDisable(false);
        call.setDisable(false);
        fold.setDisable(false);
    }

    /**
     * Method called when the round ends.
     */
    public void endOfRound() {
        if (!getChildren().contains(newRound)) {
            getChildren().add(newRound);
        }
        if (!getChildren().contains(saveGame)) {
            getChildren().add(saveGame);
        }
        disableButtons();
    }

    /**
     * saves the game data to a text file
     * data includes player chips, AI types, console text, and whose turn it is
     * @return if the game was saved successfully
     */
    public boolean saveGame() {
        String gameName = cont.getSaveName();
        if (gameName == null) {
            TextInputDialog gameSaver = new TextInputDialog();
            gameSaver.setTitle("Save Game");
            gameSaver.setHeaderText("Save your game.");
            gameSaver.setContentText("Enter the name of your game.");
            while (gameName == null) {
                Optional<String> result = gameSaver.showAndWait();
                if (result.isPresent()) {
                    gameName = result.get();
                } else {
                    return false;
                }
            }
        }
        try {
            PrintWriter pw = new PrintWriter("src/main/res/" + gameName
                + ".txt");
            String text = "";
            text += (cont.getLastFirstPlayer() + "\n");
            text += (cont.getBottomPlayer() + "\n");
            text += (cont.getBottomPlayer().getMoney() + "\n");
            text += (cont.getLeftPlayer().getClass() + "\n");
            text += (cont.getLeftPlayer().getMoney() + "\n");
            text += (cont.getTopPlayer().getClass() + "\n");
            text += (cont.getTopPlayer().getMoney() + "\n");
            text += (cont.getRightPlayer().getClass() + "\n");
            text += (cont.getRightPlayer().getMoney() + "\n");
            text += Console.getConsoleText();
            pw.write(text);
            pw.close();
            Alert saved = new Alert(AlertType.INFORMATION);
            saved.setTitle("Game saved");
            saved.setHeaderText("Your game has been saved successfully.");
            saved.setContentText("To continue your game, click on load game "
                + "then enter the name of your game, " + gameName + ".");
            saved.showAndWait();
            cont.setSaveName(gameName);
            return true;
        } catch (FileNotFoundException exc) {
            Alert cantSave = new Alert(AlertType.ERROR);
            cantSave.setTitle("Game save error");
            cantSave.setHeaderText("Your game could not be saved.");
            cantSave.setContentText("Game could not be saved. Make sure the"
                + " game name you entered was valid. Do not inclued any file "
                + "type extensions.");
            cantSave.showAndWait();
            return false;
        }
    }
}