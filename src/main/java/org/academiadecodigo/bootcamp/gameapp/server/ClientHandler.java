package org.academiadecodigo.bootcamp.gameapp.server;

import org.academiadecodigo.bootcamp.gameapp.server.lobby.Lobby;
import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.server.service.ServiceRegistry;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Cyrille on 06/07/17.
 */
public class ClientHandler implements Runnable {

    private Socket clientSocket;

    private Workable workable;
    private User user;

    private State state;

    private PrintWriter output;

    private BufferedReader input;

    public ClientHandler(Socket clientSocket, Workable workable, State state) {
        this.clientSocket = clientSocket;
        this.state = state;
        this.workable = workable;
        setupStreams();
    }

    @Override
    public void run() {

        //Handling message received login and register
        handle();

        try {
            //Handling message received Lobby and Room

            while (!clientSocket.isClosed()) {

                String message = input.readLine();

                clientLogout(message);

                System.out.println("Server received this message: " + message);
                workable.process(this, message);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                clientSocket.close();
                ((Lobby)workable).removeClientHandler(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void handle() {

        String message;

        try {

            while (state.equals(State.LOGIN)) {
                message = input.readLine();
                System.out.println("I received message from client: " + message);

                String[] messageTokens = ProtocolParser.splitMessage(message);

                if (messageTokens[ProtocolConfig.PROTOCOL].equals(ProtocolConfig.CLIENT_LOGIN)) {
                    authenticate(messageTokens[ProtocolConfig.USERNAME],
                            messageTokens[ProtocolConfig.PASSWORD]);

                } else {
                    createUser(messageTokens[ProtocolConfig.FIRSTNAME],
                            messageTokens[ProtocolConfig.USERNAME],
                            messageTokens[ProtocolConfig.PASSWORD]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clientLogout(String message) {

        String[] protocol = ProtocolParser.splitMessage(message);

        if (protocol[ProtocolConfig.PROTOCOL].equals(ProtocolConfig.CLIENT_LOGIN)){

            authenticate(protocol[ProtocolConfig.USERNAME],
                    protocol[ProtocolConfig.PASSWORD]);
        }
    }

    private void setupStreams() {

        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sending msg just for one client
    public void sendMessage(String message) {

        System.out.println("Server will send this message: " + message);
        output.write(message + "\n");
        output.flush();
    }

//----------------------------------------------------------------------------------------------------------------------
//                                               Login and Register HANDLING
//----------------------------------------------------------------------------------------------------------------------
    public void authenticate(String username, String password) {

        ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
        UserService userService = serviceRegistry.getService(UserService.class.getSimpleName());

        if (userService.authenticate(username, password)) {
            sendMessage(ProtocolConfig.SERVER_LOGIN + ";" + ProtocolConfig.VIEW_LOBBY);

            state = State.LOBBY;

            user = userService.findByName(username);
            return;
        }
        sendMessage(ProtocolConfig.SERVER_LOGIN + ";" + ProtocolConfig.ERR);
    }


    public void createUser(String firstName, String username, String password) {

        ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
        UserService userService = serviceRegistry.getService(UserService.class.getSimpleName());

        if (userService.findByName(username) == null) {

            User user = new User(firstName, username, password);

            userService.addUser(user);

            sendMessage(ProtocolConfig.SERVER_REGISTER + ";"
                    + ProtocolConfig.VIEW_LOGIN);
            return;
        }

        sendMessage(ProtocolConfig.SERVER_REGISTER + ";" + ProtocolConfig.ERR);
    }

    public void changeState(Workable workable, State state) {
        this.state = state;
        this.workable = workable;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public void setWorkable(Workable workable){
        this.workable = workable;
    }
}
