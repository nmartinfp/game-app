package org.academiadecodigo.bootcamp.gameapp.client;

import org.academiadecodigo.bootcamp.gameapp.utilities.AppConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.logging.Logger;
import org.academiadecodigo.bootcamp.gameapp.utilities.logging.PriorityLevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class Client {

    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;


    public Client() {

        try {
            clientSocket = new Socket(AppConfig.IP, AppConfig.PORT);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            Logger.getInstance().log(PriorityLevel.HIGH, "Client Socket IOException: " + e.getMessage());
        }

        settingStreams();
    }


    private void settingStreams() {

        try {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(PriorityLevel.HIGH, "Client Streams IOException: " + e.getMessage());
        }
    }


    // TODO: 04/07/17 close thread of readLine() when close program
    //Receiving message from server
    public String receive() {
        String receivedMessage = null;

        try {
            receivedMessage = input.readLine();

            System.out.println("recebi esta mensagem: " + receivedMessage);   //todo TESTING

            if (receivedMessage.equals("null")) {
                input.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(PriorityLevel.HIGH, "Client receive IOException: " + e.getMessage());
        }

        return receivedMessage;
    }


    //Sending Message from client to server
    public void send(String sendMessage) {

        if (sendMessage != null) {
            System.out.println("Message sent: " + sendMessage);  //todo TESTING
            output.write(sendMessage);
            output.flush();
        }
    }


    public void closeClient() {
        try {
            input.close();
            output.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(PriorityLevel.HIGH, "Client close client Stream: " + e.getMessage());
        }
    }

    // TODO: 2017/7/9 - Client getClientSocket not invoked!!!
    public Socket getClientSocket() {

        return clientSocket;
    }

    public boolean clientConnected() {

        return clientSocket.isClosed();
    }
}
