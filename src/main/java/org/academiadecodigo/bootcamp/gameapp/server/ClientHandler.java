package org.academiadecodigo.bootcamp.gameapp.server;

import org.academiadecodigo.bootcamp.gameapp.server.lobby.Lobby;
import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.server.service.ServiceRegistry;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolParser;
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

        System.out.println("I'm on run method of clientHandler");
        Logger.getInstance().log(PriorityLevel.INFO, "Client handler - " +
                clientSocket.getRemoteSocketAddress());
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
            Logger.getInstance().log(PriorityLevel.HIGH, "Client Handler input Stream IOException: " +
                    e.getMessage());

        } finally {
            try {
                clientSocket.close();
                ((Lobby)workable).removeClientHandler(this);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.getInstance().log(PriorityLevel.HIGH, "Client Handler close client Socket " +
                        e.getMessage());
            }
        }
    }


    private void handle() {

        String message;

        try {

            while (state.equals(State.LOGIN)) {
                message = input.readLine();
                System.out.println("I received message from client: " + message);
                Logger.getInstance().log(PriorityLevel.INFO, "Message received from client " + message);

                String[] messageTokens = ProtocolParser.splitMessage(message);

                if (messageTokens[ProtocolConfig.PROTOCOL].equals(ProtocolConfig.CLIENT_LOGIN)) {
                    System.out.println("Authenticating the user...");
                    Logger.getInstance().log(PriorityLevel.INFO, "Authenticating the user... " +
                            messageTokens[ProtocolConfig.USERNAME]);
                    authenticate(messageTokens[ProtocolConfig.USERNAME],
                            messageTokens[ProtocolConfig.PASSWORD]);
                    return;
                }

                System.out.println("Registering the user...");
                Logger.getInstance().log(PriorityLevel.INFO, "Registering the user... " +
                        messageTokens[ProtocolConfig.USERNAME]);
                createUser(messageTokens[ProtocolConfig.USERNAME],
                        messageTokens[ProtocolConfig.PASSWORD],
                        messageTokens[ProtocolConfig.FIRST_NAME]);
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
            Logger.getInstance().log(PriorityLevel.HIGH, "Client Handler setup Streams IOException: " +
                    e.getMessage());
        }
    }


    //Sending msg just for one client
    public void sendMessage(String message) {

        System.out.println("Server will send this message: " + message);
        System.out.println("Server will send this message: " + message);
        Logger.getInstance().log(PriorityLevel.INFO, "Client Handler @Server message: " + message);
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

        Logger.getInstance().log(PriorityLevel.HIGH, "Client Handler: User authentication failure" + username);
    }


    public void createUser(String firstName, String username, String password) {

        ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
        UserService userService = serviceRegistry.getService(UserService.class.getSimpleName());

        if (userService.findByName(username) == null) {

            Logger.getInstance().log(PriorityLevel.INFO, "Register user: " + firstName + " " +
                    username + " " + password);

            User user = new User(firstName, username, password);

            userService.addUser(user);

            sendMessage(ProtocolConfig.SERVER_REGISTER + ";" + ProtocolConfig.VIEW_LOGIN);

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
