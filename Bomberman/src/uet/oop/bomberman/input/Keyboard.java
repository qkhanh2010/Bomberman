package uet.oop.bomberman.input;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Keyboard {
    public static ArrayList<KeyCode> inputList =new ArrayList<KeyCode>();

    public static ArrayList<KeyCode> getInputList() {
        return inputList;
    }

    public static void keyHandle(Scene scene) {
        keyPressHandler keyPressHandler = new keyPressHandler();
        keyReleaseHandler keyReleaseHandler = new keyReleaseHandler();
        scene.setOnKeyPressed(keyPressHandler);
        scene.setOnKeyReleased(keyReleaseHandler);
    }
}

class keyPressHandler implements EventHandler<KeyEvent> {
    public keyPressHandler() {

    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        if (!Keyboard.inputList.contains(code)) {
            if (code == KeyCode.SPACE) {
                if (!Keyboard.inputList.contains(KeyCode.SPACE)) {
                    Keyboard.inputList.add(code);
                } else {
                    System.out.println("b");
                }
            } else {
                Keyboard.inputList.add(code);
            }
        }
    }
}

class keyReleaseHandler implements EventHandler<KeyEvent> {
    public keyReleaseHandler() {

    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();

        if (Keyboard.inputList.contains(code)) {
            Keyboard.inputList.remove(code);
        }
    }
}
