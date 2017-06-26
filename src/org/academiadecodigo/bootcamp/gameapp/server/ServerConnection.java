package org.academiadecodigo.bootcamp.gameapp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by codecadet Helder Matos on 26/06/17.
 */
public class ServerConnection implements Runnable {

    private Socket clientSocket;
    private BufferedReader input;

    public ServerConnection(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {

                String line = input.readLine();

                if (line.equals("null")) {
                    input.close();
                }

                    System.out.println(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
