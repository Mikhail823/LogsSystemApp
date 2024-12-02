package ru.popov.logssystemapp.dialog;

import javafx.scene.control.Alert;

public class AlertSucceeded {
    public static void alertSucceeded(String msg){
        Alert sqlErrorAlert = new Alert(Alert.AlertType.INFORMATION);
        sqlErrorAlert.setTitle("Succeeded!");
        sqlErrorAlert.setHeaderText(null);
        sqlErrorAlert.setContentText(msg);
        sqlErrorAlert.showAndWait();
    }
}
