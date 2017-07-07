package org.academiadecodigo.bootcamp.gameapp.server.room;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

import org.academiadecodigo.bootcamp.gameapp.server.ServerHandler;
import org.academiadecodigo.bootcamp.gameapp.server.Workable;
import org.academiadecodigo.bootcamp.gameapp.server.model.User;

import java.util.Vector;

/**
 * Class to handle a gameRoom, creation, adding users, removing users, and whatnot
 */
public class Room implements Workable{

    private String name;

    private Vector<User> users;
    private int minSize;
    private int maxSize;

//----CONSTRUCTORS------------------------------------------------------------------------------------------------------

    public Room(int roomSize){
        minSize = roomSize;
        maxSize = roomSize;
        users = new Vector<>();
    }

    //To be used in games that require a range of players like Secret Hitler (HYPE)
    public Room(int minSize, int maxSize){
        this.minSize = minSize;
        this.maxSize = maxSize;
        users = new Vector<>();
    }

    @Override
    public void process(ServerHandler serverHandler, String message) {

    }
//----------------------------------------------------------------------------------------------------------------------

    // TODO: 03/07/17 Create access to server, that will allow access to its user HashMap and by extension, each user
    // TODO: 03/07/17 and the socket associated with them.
    public void addUser(User user){
        users.add(user);
    }

    public String getName() {
        return name;
    }

    public int getMinSize() {
        return minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public Vector<User> getUsers() {
        return users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUserInRoom(User user){
        return users.contains(user);
    }
}
