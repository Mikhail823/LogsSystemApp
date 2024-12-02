package ru.popov.logssystemapp.controllers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Duration;
import ru.popov.logssystemapp.dto.BatteryFXDTO;
import ru.popov.logssystemapp.model.Battery;
import ru.popov.logssystemapp.service.OpenScene;
import ru.popov.logssystemapp.service.ServiceFactory;

import java.time.LocalDate;
import java.util.List;

public class BatteryJournalController {
    @FXML
    private TableView<BatteryFXDTO> batteryTable;
    @FXML
    private TableColumn<BatteryFXDTO, Integer> numberColumn;
    @FXML
    private TableColumn<BatteryFXDTO, String> codeColumn;
    @FXML
    private  TableColumn<BatteryFXDTO, LocalDate> lastDateColumn;
    @FXML
    private  TableColumn<BatteryFXDTO, Integer> countCiclColumn;
    @FXML
    private  TableColumn<BatteryFXDTO, String> conditionColumn;
    @FXML
    private  TableColumn<BatteryFXDTO, String> groupColumn;
    @FXML
    private  TableColumn<BatteryFXDTO, String> statusColumn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<?> selectGroupBox;
    @FXML
    private ComboBox<?> selectCondition;
    @FXML
    private TextField selectCode;
    @FXML
    private Label errorLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Button btnUpdateBattery;
    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    protected void initialize(){
        progressIndicator.setOpacity(0);
        updateBatteryTable();

        btnUpdateBattery.setAlignment(Pos.CENTER);

    }

    @FXML
    private void openUpdateBatteryWindow(){
        OpenScene.openScene("view/updateBattery.fxml", "Обновление данных АКБ");
    }

    private ObservableList<BatteryFXDTO> getAllBatteryTable(){
        ObservableList<BatteryFXDTO> batteryList = FXCollections.observableArrayList();

        Service<ObservableList<BatteryFXDTO>> service = new Service<ObservableList<BatteryFXDTO>>() {
            @Override
            protected Task<ObservableList<BatteryFXDTO>> createTask() {
                return new Task<ObservableList<BatteryFXDTO>>() {
                    @Override
                    protected ObservableList<BatteryFXDTO> call() throws Exception {
                        List<Battery> list = ServiceFactory.getInstance().getBatteryService().findBatteryAll();
                        for (Battery bat : list) {
                            BatteryFXDTO batteryFXDTO = new BatteryFXDTO(bat.getId(),
                                    bat.getChangeCount(),
                                    bat.getCycles(),
                                    bat.getDisChangeCount(),
                                    bat.getLastDate(),
                                    bat.getCode(),
                                    bat.getCondition(),
                                    bat.getDescr(),
                                    bat.getGroupBattery(),
                                    bat.getStatus());
                            batteryList.add(batteryFXDTO);
                        }
                        for (int i = 0; i < 100; i++) {
                            updateProgress(i, 100);
                        }

                        Platform.runLater(() -> {
                            PauseTransition pause = new PauseTransition(Duration.millis(200.0));
                            pause.setOnFinished(e -> {
                                progressIndicator.progressProperty().unbind();
                                progressIndicator.visibleProperty().unbind();
                                progressIndicator.setOpacity(1);
                                infoLabel.textProperty().unbind();

                            });
                            pause.play();
                        });
                        return batteryList;
                    }

                    @Override
                    protected void succeeded() {
                        super.succeeded();
                        updateMessage("Данные обновлены");
                    }

                    @Override
                    protected void failed() {
                        super.failed();
                        updateMessage("Произошла ошибка обновления данных из БД");
                    }

                };
            }
        };
        progressIndicator.progressProperty().bind(service.progressProperty());
        progressIndicator.visibleProperty().bind(service.runningProperty());
        infoLabel.textProperty().bind(service.messageProperty());

        if (service.getState() == Worker.State.READY){
            service.start();
        } else {
            service.restart();
        }
        return batteryList;
    }

    private void updateBatteryTable(){
        batteryTable.setItems(getAllBatteryTable());

        numberColumn.setCellValueFactory(bc -> bc.getValue().getId().asObject());
        lastDateColumn.setCellValueFactory(ld -> ld.getValue().getLastDate());
        countCiclColumn.setCellValueFactory(cc -> cc.getValue().getChangeCount().asObject());
        conditionColumn.setCellValueFactory(cond -> cond.getValue().getCondition());
        groupColumn.setCellValueFactory(gc -> gc.getValue().getGroupBattery());
        statusColumn.setCellValueFactory(sc -> sc.getValue().getStatus());
        codeColumn.setCellValueFactory(code -> code.getValue().getCode());
    }

}
