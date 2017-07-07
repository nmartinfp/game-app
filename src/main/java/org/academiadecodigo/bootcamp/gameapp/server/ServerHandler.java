package org.academiadecodigo.bootcamp.gameapp.server;

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
public class ServerHandler implements Runnable{

    private Socket clientSocket;
    private State state;
    private PrintWriter output;
    private BufferedReader input;
    private Workable workable;

    public ServerHandler(Socket clientSocket, Workable workable, State state){
        this.clientSocket = clientSocket;
        this.state = state;
        this.workable = workable;
        setupStreams();
    }

    @Override
    public void run() {

        //Login || register
        while(state.equals(State.LOGIN)) {

            try {
                String line = input.readLine();

                ProtocolParser.serverProtocolHandler(this, line);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        while(!clientSocket.isClosed()) {

            try {
                String line = input.readLine();
                workable.process(this, line);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void setupStreams() {

        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//----------------------------------------------------------------------------------------------------------------------
//                                               Login and Register HANDLING
//----------------------------------------------------------------------------------------------------------------------


    public void authenticate(String username, String password) {

        ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
        UserService userService = serviceRegistry.getService(UserService.class.getSimpleName());

        if (userService.authenticate(username, password)) {

            output.write(ProtocolConfig.SERVER_LOGIN + ProtocolConfig.VIEW_LOBBY);
            output.flush();
            state = State.LOBBY;

            return;
        }

        output.write(ProtocolConfig.SERVER_ERR);
        output.flush();
    }

    public void createUser(String firstName, String username, String password) {

        ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
        UserService userService = serviceRegistry.getService(UserService.class.getSimpleName());

        if (userService.findByName(username) == null) {

            User user = new User(firstName, username, password);

            userService.addUser(user);

            output.write(ProtocolConfig.SERVER_REGISTER + ProtocolConfig.VIEW_LOGIN);
            output.flush();
            return;
        }

        output.write(ProtocolConfig.SERVER_ERR);
        output.flush();
    }
}
