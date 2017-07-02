package org.academiadecodigo.bootcamp.gameapp.server.srvController;

import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.utilities.CommProtocol;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class SrvLoginController {

    Server server;
    UserService service;

    public SrvLoginController(Server server, UserService service) {
        this.server = server;
        this.service = service;
    }

    public String authenticate(String user, String pass) {
        System.out.print("Authenticating: ");

        if (service.authenticate(user, pass)) {

            System.out.println("authentication true");
            return CommProtocol.SERVER.getProtocol() + "lobby";
        }
        return "false";

    }
}
