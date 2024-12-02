package ru.popov.logssystemapp.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.popov.logssystemapp.MainApp;
import ru.popov.logssystemapp.dialog.AlertException;

import java.io.IOException;
import java.util.Optional;

public class OpenScene {

    public static void openScene(String window, String title){
        Stage stage = new Stage();
        stage.setTitle(title);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(window));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setResizable(false);
            stage.setOnCloseRequest(e -> {
                Alert dialog = new Alert(Alert.AlertType.CONFIRMATION,
                        "Вы действительно хотите закрыть окно?", ButtonType.NO, ButtonType.OK);
                dialog.setTitle("Закрытие окна");
                dialog.setHeaderText(null);
                dialog.initModality(Modality.WINDOW_MODAL);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    stage.getOnCloseRequest();
                } else {
                    e.consume();
                }
            });
            stage.setScene(scene);
            stage.show();

        } catch (IOException | IllegalStateException ex) {
            AlertException.alertException(ex.getMessage());
        }
    }
}
