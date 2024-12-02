package ru.popov.logssystemapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(Thread.currentThread().getName());
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        try{
            stage.getIcons().add(new Image(getClass().getResourceAsStream("img/e.png")));
            stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Вы точно собираетесь завершить работу?");
                alert.setTitle("Зкрытие приложения");
                alert.setHeaderText(null);
                alert.setContentText("Зачем?? Не надо.... а....ааааааа");
                alert.showAndWait();
            });
        }catch (Exception e){
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Ошибка получения путик иконке");
            dialog.setHeaderText(null);
           dialog.setContentText(e.getMessage());
           dialog.showAndWait();
        }
        stage.setResizable(false);
        stage.setTitle("Электронный журнал");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public void stop() throws Exception {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        launch();
    }
}