package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Cyrille on 02/07/17.
 */
public class CltRpsController implements Initializable , Controller{

    private final String NAME = "RPS";
    @Override

    public void initialize(URL location, ResourceBundle resources) {

    }

    public String getName(){
        return NAME;
    }
}
