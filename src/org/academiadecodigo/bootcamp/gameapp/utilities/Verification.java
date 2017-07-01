package org.academiadecodigo.bootcamp.gameapp.utilities;

import javafx.scene.control.Label;

/**
 * Created by codecadet Helder Matos on 01/07/17.
 */
public class Verification {

    public static void cleanErrorMsg(Label lblUsernameError, Label lblPasswordError, Label lblMailErrorReg) {
        lblUsernameError.setVisible(false);
        lblPasswordError.setVisible(false);
        lblMailErrorReg.setVisible(false);
    }

}
