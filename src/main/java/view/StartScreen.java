package view;

import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.io.FileNotFoundException;
import javafx.scene.layout.HBox;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class StartScreen extends StackPane {

    // Path to the image file for the background
    private static final String BACK_LOCATION = "File:./src/main/res"
        + "/poker-game-background.png";
    private static final Image BACK_IMAGE = new Image(BACK_LOCATION);
    private Button startButton;
    private TextInputDialog confirm;
    private Button loadGame;
    private TextInputDialog gameNameGetter;

    /**
     * StartScreen's constructor
     * @param cont The PokerGame to interact with
     */
    public StartScreen(PokerGame cont) {
        ImageView background = new ImageView(BACK_IMAGE);
        background.setFitHeight(1275.0 / 2);
        background.setFitWidth(1920.0 / 2);
        startButton = new Button("Start New Game");
        confirm = new TextInputDialog();
        confirm.setTitle("New Game");
        confirm.setHeaderText("Confirmation");
        confirm.setContentText("Enter your name:");
        startButton.setOnAction(e -> {
                Optional<String> result = confirm.showAndWait();
                if (result.isPresent()) {
                    String name = result.get().trim();
                    if (name.equals("")) {
                        name = "Human player";
                    }
                    cont.newGame(name);
                }
            });
        loadGame = new Button("Load Saved Game");
        gameNameGetter = new TextInputDialog();
        gameNameGetter.setTitle("Load Game");
        gameNameGetter.setHeaderText("Load a previous saved game.");
        gameNameGetter.setContentText("Enter the name of your game.");
        loadGame.setOnAction(e -> {
                boolean keepGoing = true;
                while (keepGoing) {
                    Optional<String> result = gameNameGetter.showAndWait();
                    if (result.isPresent()) {
                        try {
                            cont.loadGame(result.get());
                            keepGoing = false;
                        } catch (FileNotFoundException exc) {
                            gameNameGetter.setContentText("Game not found. "
                                + "Please enter the name exactly as you did "
                                + "when you saved it.");
                        }
                    } else {
                        keepGoing = false;
                    }
                }
            });
        HBox buttons = new HBox(30, startButton, loadGame);
        buttons.setAlignment(Pos.BOTTOM_LEFT);
        getChildren().addAll(background, buttons);
        buttons.setPadding(new Insets(50));
    }
}