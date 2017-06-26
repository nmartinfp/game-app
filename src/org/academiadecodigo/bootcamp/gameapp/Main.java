package org.academiadecodigo.bootcamp.gameapp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp.gameapp.client.Client;
import org.academiadecodigo.bootcamp.gameapp.client.Navigation;
import org.academiadecodigo.bootcamp.gameapp.client.controller.LoginController;
import org.academiadecodigo.bootcamp.gameapp.server.Server;

import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    private PrintWriter output;

    public static void main(String[] args) {

        if (args[0].equals("server")) {
            (Executors.newSingleThreadExecutor()).submit(new Server());
        }

        launch(args);
    }

    @Override
    public void init() {
        output = (new Client()).getOut();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Navigation.getInstance().setStage(primaryStage);
        Navigation.getInstance().loadScreen("login");
        //Wire
        ((LoginController) Navigation.getInstance().getController("login")).setOutput(output);

    }


}
