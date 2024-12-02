package ru.popov.logssystemapp.dialog;

import javafx.scene.control.Alert;

public class AlertSQLException {
    public static void sqlException(String msg){
        Alert sqlErrorAlert = new Alert(Alert.AlertType.ERROR);
        sqlErrorAlert.setTitle("Ошибка базы данных");
        sqlErrorAlert.setHeaderText(null);
        sqlErrorAlert.setContentText(msg);
        sqlErrorAlert.showAndWait();
    }
}
