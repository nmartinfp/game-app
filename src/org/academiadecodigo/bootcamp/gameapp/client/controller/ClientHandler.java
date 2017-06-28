package org.academiadecodigo.bootcamp.gameapp.client.controller;

import org.academiadecodigo.bootcamp.gameapp.client.Client;

/**
 * Created by Cyrille on 27/06/17.
 */
public class ClientHandler implements Runnable {

    private LoginController loginController;
    private Client client;

    public ClientHandler(Client client, LoginController loginController) {
        this.client = client;
        this.loginController = loginController;
    }

    @Override
    public void run() {

        while (true) {

            System.out.println("Estou no clientHandler antes do call ontext");

            // TODO: 27/06/17 Thread blocking here, why is it not reading? nc works fine
            String recieveMessage;
            recieveMessage = client.receive();

            System.out.println("Messagem recebida: " + recieveMessage);
            loginController.onText(recieveMessage);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

