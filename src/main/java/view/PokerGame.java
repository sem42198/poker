package view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import java.util.Scanner;

import viewcontroller.PokerGameController;
import viewcontroller.GameState;
import javafx.scene.layout.VBox;
import java.util.Optional;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class PokerGame extends Application {

    private static Stage primaryStage;
    private PokerGameController controller;
    private GameScreen display;
    private ControlPane controls;
    private Console console;
    private ArrayList<String> consoleText = new ArrayList<>();

    /**
     * this method is called upon running/launching the application
     * this method should display a scene on the stage
     * @param ps The primary Stage
     */
    public void start(Stage ps) {
        primaryStage = ps;
        Scene start = new Scene(new StartScreen(this), 1920.0 / 2, 1275.0 / 2);
        primaryStage.setScene(start);
        primaryStage.setTitle("EXTREME Poker");
        primaryStage.show();
    }

    /**
     * Starts the Game
     * This is called by StartScreen whenever it is done and the GameScreen,
     * ControlPane, and Console should be displayed
     */
    public void startGame() {
        display = new GameScreen(controller);
        controls = new ControlPane(controller);
        console = new Console();
        for (int i = consoleText.size() - 1; i >= 0; i--) {
            console.putMessage(consoleText.get(i));
        }
        VBox root = new VBox(display, controls, console);
        Scene game = new Scene(root, 1920.0 / 2, 1275.0 / 2);
        primaryStage.setScene(game);
        controller.start();
    }

    /**
     * sets up a new poker game
     * @param name the human player's name
     */
    public void newGame(String name) {
        TextInputDialog setChips = new TextInputDialog();
        setChips.setTitle("Set starting chips");
        setChips.setHeaderText("Set the number of chips players chart with.");
        setChips.setContentText("Enter an integer value.");
        int startingChips = -1;
        boolean keepGoing = true;
        while (keepGoing) {
            Optional<String> result = setChips.showAndWait();
            if (result.isPresent()) {
                try {
                    startingChips = Integer.parseInt(result.get());
                    if (startingChips <= 0) {
                        throw new Throwable();
                    }
                    keepGoing = false;
                } catch (Throwable exc) {
                    startingChips = -1;
                    setChips.setContentText("Must enter positive integer.");
                }
            } else {
                return;
            }
        }
        controller = new PokerGameController(this, name, startingChips);
        startGame();
    }

    /**
     * sets up a countinued game with data from a text file
     * @param  fileName the name of the file with the saved game data
     * @throws FileNotFoundException if no file with that name is found
     */
    public void loadGame(String fileName) throws FileNotFoundException {
        int lastFirstPlayer;
        String name;
        int humanMoney;
        int[] aiTypes = new int[3];
        int[] aiMoney = new int[3];
        File game = new File("src/main/res/" + fileName + ".txt");
        Scanner sc = new Scanner(game);
        lastFirstPlayer = Integer.parseInt(sc.nextLine());
        name = sc.nextLine();
        humanMoney = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < 3; i++) {
            switch (sc.nextLine()) {
            case "class model.BluffingAI":
                aiTypes[i] = 0;
                break;
            case "class model.ConservativeAI":
                aiTypes[i] = 1;
                break;
            case "class model.RiskTakerAI":
                aiTypes[i] = 2;
                break;
            default:
                aiTypes[i] = 3;
                break;
            }
            aiMoney[i] = Integer.parseInt(sc.nextLine());
        }
        while (sc.hasNext()) {
            consoleText.add(sc.nextLine());
        }
        controller = new PokerGameController(this, name, humanMoney, aiMoney,
            lastFirstPlayer, aiTypes);
        controller.setSaveName(fileName);
        startGame();
    }

    /**
     * This is called by PokerGameController whenever updates are made. You
     * must handle updating the UI here.
     */
    public void updatesMade() {
        GameState state = controller.getState();
        display.updatesMade();
        if (state.equals(GameState.DONE)) {
            display.endOfRound();
            controls.endOfRound();
        } else {
            // display.updatesMade();
            if (state.equals(GameState.HUMAN_BET)) {
                controls.playerTurn();
            } else {
                controls.disableButtons();
            }
        }
    }

    /**
     * This is the main method that launches the javafx application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}