package org.academiadecodigo.bootcamp.gameapp.utilities;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by codecadet Helder Matos on 01/07/17.
 */
public class Verification {

    public static void cleanErrorMsg(Label lblUsernameError, Label lblPasswordError, Label lblFirstNameErrorReg, Label lblMailErrorReg) {

        lblUsernameError.setVisible(false);
        lblPasswordError.setVisible(false);
        lblFirstNameErrorReg.setVisible(false);
        lblMailErrorReg.setVisible(false);
    }

    public static boolean checkPassword(PasswordField password) {
        boolean check = false;

        if (password.getText().matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")) {
            check = true;
        }
        return check;
    }

    public static boolean checkEmail(TextField email) {
        boolean checkMail;

        if (email.getText().matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")) {
            checkMail = true;
        } else {
            checkMail = false;
        }

        return checkMail;
    }
}
