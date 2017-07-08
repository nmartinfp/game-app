package org.academiadecodigo.bootcamp.gameapp.client;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public final class Navigation {

    private final String PREFIX = "/view/";
    private final String SUFFIX = ".fxml";

    private static Navigation instance;

    private LinkedList<Scene> scenes = new LinkedList<>();
    private Map<String, Initializable> controllers = new HashMap<>();

    private Stage stage;
    private Scene scene;

    private Navigation() {}

    public void loadScreen(String view) {
        try {

            String path = PREFIX + view + SUFFIX;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root = fxmlLoader.load();

            controllers.put(view, fxmlLoader.<Initializable>getController());

            scene = new Scene(root);
            scenes.push(scene);

            setScene(scene);

        } catch (IOException e) {
            System.err.println("Failure to load view " + view + " : " + e.getMessage());
        }
    }

    public void back() {

        if (scenes.size() < 2) {
            return;
        }

        scenes.pop();
        setScene(scenes.peek());
    }

    public static Navigation getInstance() {

        if (instance == null) {

            synchronized (Navigation.class) {
                if (instance == null) {
                    instance = new Navigation();
                }
            }
        }
        return instance;
    }

    // TODO: 08/07/17 refactor down cast
    public <T extends Initializable> T getController(String view) {
        return (T)controllers.get(view);
    }


    public void setScene(Scene scene) {

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
