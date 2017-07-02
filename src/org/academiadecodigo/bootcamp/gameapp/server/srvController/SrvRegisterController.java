package org.academiadecodigo.bootcamp.gameapp.server.srvController;

import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;

/**
 * Created by codecadet on 01/07/2017.
 */
public class SrvRegisterController {


    private Server server;
    private UserService userService;

    public SrvRegisterController(Server server, UserService userService) {

        this.server = server;
        this.userService = userService;
    }

    public void createUser(String firstName, String username, String password) {

        User user;

        //String[] createUserValues = createUserString.split(" ");

        user = new User(firstName, username, password);

        userService.addUser(user);

        server.out("login");
    }
}
