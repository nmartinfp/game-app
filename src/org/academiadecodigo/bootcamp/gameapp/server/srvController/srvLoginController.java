package org.academiadecodigo.bootcamp.gameapp.server.srvController;

import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;

/**
 * Created by codecadet on 01/07/17.
 */
public class srvLoginController {

    Server server;
    UserService service;

    public srvLoginController(Server server, UserService service){
        this.server = server;
        this.service = service;
    }

    public void authenticate(String user, String pass){
        System.out.print("Authenticating: ");
        System.out.print(service.authenticate(user, pass));

    }
}
