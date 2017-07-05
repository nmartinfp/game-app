package org.academiadecodigo.bootcamp.gameapp.utilities;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public final class Verification {

    public static void cleanErrorMsg(Label lblUsernameError, Label lblPasswordError, Label lblFirstNameErrorReg, Label lblMailErrorReg) {

        lblUsernameError.setVisible(false);
        lblPasswordError.setVisible(false);
        lblFirstNameErrorReg.setVisible(false);
        lblMailErrorReg.setVisible(false);
    }

    public static boolean checkPassword(PasswordField password) {
        boolean check = false;

        if (password.getText().matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])" +
                "(?=.*[A-Z])(?=.*[a-z]).*$")) {

            check = true;
        }
        return check;
    }

    public static boolean checkEmail(TextField email) {
        boolean checkMail = false;

        if (email.getText().matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:" +
                "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")) {

            checkMail = true;
        }
        return checkMail;
    }
}
