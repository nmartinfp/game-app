package org.academiadecodigo.bootcamp.gameapp.server.service.user;

import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.server.service.Service;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public interface UserService extends Service {

    boolean authenticate(String username, String password);

    void addUser(User user);

    User findByName(String name);

}
