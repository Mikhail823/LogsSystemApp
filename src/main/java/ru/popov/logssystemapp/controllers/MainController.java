package ru.popov.logssystemapp.controllers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import ru.popov.logssystemapp.dao.FactoryDAO;
import ru.popov.logssystemapp.dialog.AlertException;
import ru.popov.logssystemapp.dialog.AlertSucceeded;
import ru.popov.logssystemapp.dto.EmployeesNameFXDTO;
import ru.popov.logssystemapp.dto.JournalFXDTO;
import ru.popov.logssystemapp.model.Battery;
import ru.popov.logssystemapp.model.Employees;
import ru.popov.logssystemapp.model.Journal;
import ru.popov.logssystemapp.model.Radiostation;
import ru.popov.logssystemapp.service.OpenScene;
import ru.popov.logssystemapp.service.ServiceFactory;
import ru.popov.logssystemapp.util.Case;
import ru.popov.logssystemapp.util.Changer;
import ru.popov.logssystemapp.util.OperationAction;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController {

    @FXML
    private TableView<JournalFXDTO> journalTable;
    @FXML
    private TableColumn<JournalFXDTO, Integer> numberColumn;
    @FXML
    private TableColumn<JournalFXDTO, LocalDate> dateColumn;
    @FXML
    private TableColumn<JournalFXDTO, String> nameColumn;
    @FXML
    private TableColumn<JournalFXDTO, String> zavNumberColumn;
    @FXML
    private TableColumn<JournalFXDTO, String> brNumberColumn;
    @FXML
    private TableColumn<JournalFXDTO, String> caseColumn;
    @FXML
    private TableColumn<JournalFXDTO, String> changerColumn;
    @FXML
    private TableColumn<JournalFXDTO, String> batteryColumn;
    @FXML
    private TableColumn<JournalFXDTO, String> headsetColumn;
    @FXML
    private ChoiceBox<String> choiceBoxData;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<EmployeesNameFXDTO> employeeComboBox;
    @FXML
    private TextArea inputText;
    @FXML
    private ComboBox<String> changerComboBox;
    @FXML
    private ProgressBar mainProgressBar;
    @FXML
    private Button btnSave;
    @FXML
    private Label infoLabel;
    @FXML
    private Label numberOfIssued;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnPrintMain;

    private AtomicReference<Integer> numberIssued = new AtomicReference<>(0);

    @FXML
    protected void initialize() {
        updateDataTableJournal(getAllDataJournal());
        Platform.runLater(inputText::requestFocus);
        employeeComboBox.setItems(getAllEmployees());
        changerComboBox.setItems(getChargerList());
        changingTheBackgroundColorByDate();

        btnUpdate.setOnAction(e -> {
            updateDataTableJournal(getDataByDate());
        });

        btnPrintMain.setOnAction(e -> {
            printData(journalTable);
        });
    }

    /**
     * TODO
     * Метод для записи в журнал выдачи радиостанций
     */
    @FXML
    private void onActionSaveDataJournal() {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Employees employees = ServiceFactory.getInstance()
                                .getEmployeesService().findEmployeeByName(String.valueOf(employeeComboBox.getValue()));

                        if (splitInputText(inputText).length == 1) {
                            if (matcherRegex(splitInputText(inputText)[0]).find()) {
                                issuingOneBattery(employees);
                            } else {
                                AlertException.alertException("Данные АКБ отсутствуют в БД!");
                            }
                        } else {
                            Radiostation radio = ServiceFactory.getInstance().getRadiostationService()
                                .findRadioStationByNumber(splitInputText(inputText)[0]);
                            issuingARadioStation(radio, employees);
                        }

                        for (int i = 0; i < 101; i++) {
                            updateProgress(i, 100);
                        }

                        Platform.runLater(() -> {
                            PauseTransition pause = new PauseTransition(Duration.millis(200.0));
                            pause.setOnFinished(e -> {
                                mainProgressBar.progressProperty().unbind();
                                mainProgressBar.visibleProperty().unbind();
                                infoLabel.textProperty().unbind();

                                updateDataTableJournal(getAllDataJournal());

                                employeeComboBox.setValue(null);
                                changerComboBox.setValue(null);
                                inputText.clear();
                            });
                            pause.play();
                        });
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        super.succeeded();
                        updateMessage("Запись успешна!!!");
                        AlertSucceeded.alertSucceeded("Молодец записал!!!");
                    }

                    @Override
                    protected void failed() {
                        super.failed();
                        updateMessage("Произошла ошибка записи данных в БД");
                        AlertException.alertException("Возникла ошибка сохранения в БД. Проверьте входные данные.");
                    }
                };
            }
        };

        mainProgressBar.progressProperty().bind(service.progressProperty());
        mainProgressBar.visibleProperty().bind(service.runningProperty());
        infoLabel.textProperty().bind(service.messageProperty());

        if (service.getState() == Worker.State.READY) {
            service.start();
        } else {
            service.restart();
        }
    }

    /**
     * TODO
     * Метод для удаления из журнала
     *
     * @return
     */
    @FXML
    private void onActionWriteOff() {
        Service<Void> serviceWrite = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        JournalFXDTO journalFXDTO = journalTable.getSelectionModel().getSelectedItem();
                        if (journalFXDTO != null) {
                            journalTable.getItems().remove(journalFXDTO);
                            Battery battery = ServiceFactory.getInstance().getBatteryService().findBatteryByCode(journalFXDTO.getBattery().get());
                            battery.setStatus("");
                            FactoryDAO.getInstance().getBatteryDAO().updateBattery(battery);
                            ServiceFactory.getInstance().getJournalService().deleteById(journalFXDTO.getId().getValue());
                        }

                        for (int i = 0; i < 100; i++) {
                            updateProgress(i, 100);
                        }

                        Platform.runLater(() -> {
                            PauseTransition pause = new PauseTransition(Duration.millis(200.0));
                            pause.setOnFinished(e -> {
                                mainProgressBar.progressProperty().unbind();
                                mainProgressBar.visibleProperty().unbind();
                                infoLabel.textProperty().unbind();

                                numberIssued.set(journalTable.getItems().size());
                                numberOfIssued.setText(numberIssued.toString());
                            });
                            pause.play();
                        });
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        super.succeeded();
                        updateMessage("Радиостанция списана");
                    }

                    @Override
                    protected void failed() {
                        super.failed();
                        updateMessage("Произошла ошибка обновления данных из БД");
                    }
                };
            }
        };

        mainProgressBar.progressProperty().bind(serviceWrite.progressProperty());
        mainProgressBar.visibleProperty().bind(serviceWrite.runningProperty());
        infoLabel.textProperty().bind(serviceWrite.messageProperty());

        if (serviceWrite.getState() == Worker.State.READY) {
            serviceWrite.start();
        } else {
            serviceWrite.restart();
        }
    }

    private ObservableList<JournalFXDTO> getAllDataJournal() {
        ObservableList<JournalFXDTO> list = FXCollections.observableArrayList();
        Task<ObservableList<JournalFXDTO>> task = new Task<ObservableList<JournalFXDTO>>() {
            @Override
            protected ObservableList<JournalFXDTO> call() throws Exception {
                List<Journal> journals =
                        ServiceFactory.getInstance().getJournalService().getAllDataJournal();
                for (Journal j : journals) {
                    JournalFXDTO journalFXDTO =
                            new JournalFXDTO(j.getId(),
                                    j.getName(),
                                    j.getZavNumber(),
                                    j.getDateReceipt(),
                                    j.getOrganization(),
                                    j.getEquipment(),
                                    j.getBrNumber(),
                                    j.getChexol(),
                                    j.getChanger(),
                                    j.getBattery());
                    list.add(journalFXDTO);
                }
                for (int i = 0; i < 100; i++) {
                    updateProgress(i, 100);
                }
                Platform.runLater(() -> {
                    PauseTransition pause = new PauseTransition(Duration.millis(200.0));
                    pause.setOnFinished(e -> {
                        mainProgressBar.progressProperty().unbind();
                        mainProgressBar.visibleProperty().unbind();
                        infoLabel.textProperty().unbind();
                        numberIssued.set(list.size());
                        numberOfIssued.setText(numberIssued.toString());
                    });
                    pause.play();
                });
                return list;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                updateMessage("Данные журнала обновлены");
            }

            @Override
            protected void failed() {
                super.failed();
                updateMessage("Произошла ошибка обновления данных из БД");
            }
        };

        mainProgressBar.progressProperty().bind(task.progressProperty());
        mainProgressBar.visibleProperty().bind(task.runningProperty());
        infoLabel.textProperty().bind(task.messageProperty());

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

        return list;
    }

    private ObservableList<EmployeesNameFXDTO> getAllEmployees() {
        ObservableList<EmployeesNameFXDTO> listEmployee = FXCollections.observableArrayList();
        Task<ObservableList<EmployeesNameFXDTO>> task = new Task<ObservableList<EmployeesNameFXDTO>>() {
            @Override
            protected ObservableList<EmployeesNameFXDTO> call() throws Exception {
                List<String> st = ServiceFactory.getInstance().getEmployeesService().getAllNameEmployees();
                if (!st.isEmpty()) {
                    for (String d : st) {
                        EmployeesNameFXDTO name = new EmployeesNameFXDTO(d);
                        listEmployee.add(name);
                    }
                }
                for (int i = 0; i < 100; i++) {
                    updateProgress(i, 100);
                }
                Platform.runLater(() -> {
                    PauseTransition pause = new PauseTransition(Duration.millis(200.0));
                    pause.setOnFinished(e -> {
                        mainProgressBar.progressProperty().unbind();
                        mainProgressBar.visibleProperty().unbind();
                        infoLabel.textProperty().unbind();
                    });
                    pause.play();
                });
                return listEmployee;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                updateMessage("Данные из БД получены");
            }

            @Override
            protected void failed() {
                super.failed();
                updateMessage("Произошла ошибка получения данных из БД");
            }
        };

        mainProgressBar.progressProperty().bind(task.progressProperty());
        mainProgressBar.visibleProperty().bind(task.runningProperty());
        infoLabel.textProperty().bind(task.messageProperty());

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

        return listEmployee;
    }

    @FXML
    private void onClickOpenNewSave() {
        OpenScene.openScene("view/newEmployeee.fxml", "Добавление нового сотрудника");
    }

    @FXML
    private void onTypedKeyAction(KeyEvent keyEvent) {
        if (keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
            splitInputText(inputText);
        }
    }

    @FXML
    private void onClickOpenBatteryJournalView() {
        OpenScene.openScene("view/battery.fxml", "Аккумуляторный журнал");
    }

    private ObservableList<String> getChargerList() {
        ObservableList<String> listChanger = FXCollections.observableArrayList();
        listChanger.add(Changer.CD.toString());
        listChanger.add(Changer.NB.toString());
        listChanger.add(Changer.NBU.toString());
        listChanger.add(Changer.VAC.toString());
        return listChanger;
    }


    private ObservableList<JournalFXDTO> getDataByDate() {
        List<Journal> j = ServiceFactory.getInstance().getJournalService().getListJournalByDate(datePicker.getValue());
        JournalFXDTO fxdto = null;
        ObservableList<JournalFXDTO> observableList = FXCollections.observableArrayList();
        for (Journal l : j) {
            fxdto = new JournalFXDTO(l.getId(), l.getName(), l.getZavNumber(), l.getDateReceipt(),
                    l.getOrganization(), l.getEquipment(), l.getBrNumber(), l.getChexol(), l.getChanger(), l.getBattery());
            observableList.add(fxdto);
        }

        return observableList;
    }

    private void updateDataTableJournal(ObservableList<JournalFXDTO> list) {
        journalTable.setItems(list);
        numberColumn.setCellValueFactory(i -> i.getValue().getId().asObject());
        dateColumn.setCellValueFactory(d -> d.getValue().getDateReceipt());
        nameColumn.setCellValueFactory(n -> n.getValue().getName());
        zavNumberColumn.setCellValueFactory(nu -> nu.getValue().getZavNumber());
        brNumberColumn.setCellValueFactory(br -> br.getValue().getBrNumber());
        headsetColumn.setCellValueFactory(ee -> ee.getValue().getEquipment());
        caseColumn.setCellValueFactory(cc -> cc.getValue().getChexol());
        changerColumn.setCellValueFactory(ch -> ch.getValue().getChanger());
        batteryColumn.setCellValueFactory(bat -> bat.getValue().getBattery());
    }

    private void changingTheBackgroundColorByDate() {

        dateColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean b) {
                super.updateItem(date, b);
                if (b || date == null) {
                    setText(null);
                } else {
                    setText(date.toString());
                    if (date.isBefore(LocalDate.now())) {
                        setTextFill(Color.RED);
                    } else {
                        setTextFill(Color.BLACK);
                    }
                }
            }
        });
    }

    private String[] splitInputText(TextArea inputText) {
        String[] list = inputText.getText().split("\n");
        return list;
    }

    private Matcher matcherRegex(String text){
        String regex = "^[714]";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text);
    }

    private void issuingOneBattery(Employees employees){
        Battery battery = ServiceFactory.getInstance().getBatteryService()
                .findBatteryByCode(splitInputText(inputText)[0]);
        if (battery != null){
            Journal journal =
                    new Journal(employees.getName(),
                            null,
                            LocalDate.now(),
                            employees.getOrgan(),
                            null,
                            null,
                            null,
                            null,
                            splitInputText(inputText)[0]);
            ServiceFactory.getInstance().getJournalService().save(journal);
            battery.setStatus(OperationAction.ON_THE_ISSUE.toString());
            FactoryDAO.getInstance().getBatteryDAO().updateBattery(battery);
        }else {
            AlertException.alertException("Полученные данные отсутствуют в БД!");
        }

    }

    private void issuingARadioStation(Radiostation radiostation, Employees employees){
        Battery battery = ServiceFactory.getInstance().getBatteryService()
                .findBatteryByCode(splitInputText(inputText)[1]);
        if (battery != null && radiostation != null){
            if (radiostation.getIsChexol() && radiostation.getGarnitura() == null) {
                Journal journal = new Journal(employees.getName(),
                        splitInputText(inputText)[0],
                        LocalDate.now(),
                        employees.getOrgan(),
                        radiostation.getGarnitura(),
                        radiostation.getBrNumber(),
                        Case.WITH_A_CASE.toString(),
                        changerComboBox.getValue(),
                        splitInputText(inputText)[1]);

                ServiceFactory.getInstance().getJournalService().save(journal);
            } else if (radiostation.getGarnitura() != null && !radiostation.getIsChexol()) {
                Journal journal = new Journal(employees.getName(),
                        splitInputText(inputText)[0],
                        LocalDate.now(),
                        employees.getOrgan(),
                        radiostation.getGarnitura(),
                        radiostation.getBrNumber(),
                        Case.WITHOUT_A_COVER.toString(),
                        changerComboBox.getValue(),
                        splitInputText(inputText)[1]);
                ServiceFactory.getInstance().getJournalService().save(journal);
            }
            battery.setStatus(OperationAction.ON_THE_ISSUE.toString());
            FactoryDAO.getInstance().getBatteryDAO().updateBattery(battery);
        } else {
            AlertException.alertException("Полученные данные отсутствуют в БД!");
        }
    }


    private void printData(TableView<?> tableView){
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null){
            boolean succes = printerJob.showPrintDialog(tableView.getScene().getWindow());
            if (succes){
                Node tableToPrint = tableView;
                double scale = 0.85;
                tableToPrint.getTransforms().add(new Scale(scale, scale));
                boolean prented = printerJob.printPage(tableToPrint);
                if (prented){
                    printerJob.endJob();
                }
            }
        }
    }
}
