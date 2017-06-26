package org.academiadecodigo.bootcamp.gameapp.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by codecadet Helder Matos on 26/06/17.
 */
public class Client {

    private final int PORT_NUMBER = 1234;
    private final InetAddress ip = InetAddress.getLoopbackAddress();
    private Socket clientSocket;
    private PrintWriter out;

    public Client(){

        try {
            clientSocket = new Socket(ip, PORT_NUMBER);

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }

        settingStreams();
    }

    private void settingStreams() {

        try {

            out = new PrintWriter(clientSocket.getOutputStream(), true);

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public PrintWriter getOut(){
        return out;
    }


}
