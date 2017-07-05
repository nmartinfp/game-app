package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
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
