package ru.popov.logssystemapp.dialog;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class AlertException {

    public static Alert alertException(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Возникла ошибка!!!");
        alert.setContentText(msg);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.showAndWait();
        return alert;
    }
}
