package ru.popov.logssystemapp.controllers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import ru.popov.logssystemapp.dialog.AlertSQLException;
import ru.popov.logssystemapp.dialog.AlertSucceeded;
import ru.popov.logssystemapp.model.Employees;
import ru.popov.logssystemapp.service.ServiceFactory;

public class NewEmployeeController {
    @FXML
    private TextField employeeField;
    @FXML
    private TextField organizationField;
    @FXML
    private ProgressBar progressBarMain;

    @FXML
    protected void initialize(){
        progressBarMain.setOpacity(0.0);

    }

    @FXML
    private void onClickClearDate(){
        employeeField.clear();
        organizationField.clear();
    }

    @FXML
    private void onClickSaveNewEmployee(){
        Service<Void> serviceSave = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        Employees newEmployee = new Employees(employeeField.getText(), organizationField.getText());
                        ServiceFactory.getInstance().getEmployeesService().saveEmployee(newEmployee);
                        for (int i = 0; i<101; i++){
                            updateProgress(i, 100);
                        }
                        Platform.runLater(() -> {
                            PauseTransition pause = new PauseTransition(Duration.millis(200.0));
                            pause.setOnFinished(e -> {
                                progressBarMain.opacityProperty().unbind();
                                progressBarMain.progressProperty().unbind();
                                progressBarMain.visibleProperty().unbind();
                                employeeField.clear();
                                organizationField.clear();
                            });
                            pause.play();
                        });
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        super.succeeded();
                        updateMessage("Запись произведена успешна!!!");
                        AlertSucceeded.alertSucceeded("Новый сотрудник записан в БД");
                    }

                    @Override
                    protected void failed() {
                        super.failed();
                        updateMessage("Произошла ошибка записи данных в БД");
                        AlertSQLException.sqlException("Ошибка записи в БД!");
                    }

                };
            }
        };

        progressBarMain.progressProperty().bind(serviceSave.progressProperty());
        progressBarMain.visibleProperty().bind(serviceSave.runningProperty());

        progressBarMain.setOpacity(1.0);
        if (serviceSave.getState() == Worker.State.READY){
            serviceSave.start();
        } else {
            serviceSave.restart();
        }
    }
}
