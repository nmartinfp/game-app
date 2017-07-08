package org.academiadecodigo.bootcamp.gameapp.server.service.user;

import org.academiadecodigo.bootcamp.gameapp.server.model.User;

import java.sql.*;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class JdbcUserService implements UserService {

    private Connection dbConnection;

    public JdbcUserService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }


    @Override
    public boolean authenticate(String username, String password) {

        User user = findByName(username);

        if (user != null && user.getPassword().equals(password)) {

            return true;
        }
        return false;
    }

    @Override
    public void addUser(User user) {

        try {
            if (findByName(user.getUsername()) == null) {


                // Create a query
                String query = "INSERT INTO users (first_name, username, password) VALUES (?, ? , ?)";


                // Create a new statement
                PreparedStatement statement = dbConnection.prepareStatement(query);


                // Set values for the placeholders
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getPassword());

                // Execute the query
                statement.executeUpdate();


                // Close statement
                closeStatement(statement);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failure to connect to database : " + e.getMessage());
        }
    }

    @Override
    public User findByName(String name) {

        User user;

        try {

            // Create a query
            String query = "SELECT username, password FROM users WHERE username = ?";

            // Create a new statement
            PreparedStatement statement = dbConnection.prepareStatement(query);

            // Set values for the placeholders
            statement.setString(1, name);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Get the results
            if (resultSet.next()) {
                String usernameValue = resultSet.getString("username");
                String passwordValue = resultSet.getString("password");

                user = new User(usernameValue, passwordValue);

                return user;
            }

            closeStatement(statement);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failure to connect to database : " + e.getMessage());
        }

        return null;
    }

    @Override
    public String getName() {
        return UserService.class.getSimpleName();
    }

    private void closeStatement(Statement statement) throws SQLException {

        if (statement != null) {
            statement.close();
        }
    }
}
