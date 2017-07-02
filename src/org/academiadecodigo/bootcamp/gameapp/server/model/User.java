package org.academiadecodigo.bootcamp.gameapp.server.model;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class User {

    private String firstName;
    private String password;
    private String username;
    private String lastName;
    private String email;

    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public User(String firstName, String username, String password) {
        this.firstName = firstName;
        this.username = username;
        this.password = password;
    }

    // TODO: 01/07/2017 - Fix this shit please!!!!
    public User(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String email) {
        this.firstName = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
