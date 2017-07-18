package view;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

/**
 * @author CS1331 TAs
 * @version 1.0
 */
public class Console extends ScrollPane {

    private static Console instance;
    private Label label;
    /**
     * Console's constructor. Set's the static instance variable.
     */
    public Console() {
        instance = this;
        label = new Label();
        label.setWrapText(true);
        setContent(label);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setPrefHeight(113);
        label.setMaxWidth(1890.0 / 2);
    }

    /**
     * Add's text to the top of the console. (Doesn't get rid of
     * text that is already there!)
     * @param newText is the text to add to the top of the console
     */
    public void addText(String newText) {
        label.setText(String.format("%s%n%s", newText, label.getText()));
        setVvalue(0.0);
    }

    /**
     * Clears the console of any text
     */
    public void clear() {
        label.setText("");
    }

    /**
     * gets the text within console's label
     * @return the text from console's label
     */
    public String getText() {
        return label.getText();
    }

    /**
     * static method that gets text from current
     * {@value instance}
     * @return text from instance's label
     */
    public static String getConsoleText() {
        return instance.getText();
    }

    /**
     * Static method that adds a message into the current
     * {@value  instance}
     * @param message The message to add
     */
    public static void putMessage(String message) {
        instance.addText(message);
    }

    /**
     * Clears the console of the current {@value instance}
     */
    public static void clearLog() {
        instance.clear();
    }
}