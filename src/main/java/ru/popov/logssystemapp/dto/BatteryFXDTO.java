package ru.popov.logssystemapp.dto;


import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class BatteryFXDTO {
    private IntegerProperty id;
    private IntegerProperty changeCount;
    private IntegerProperty cycles;
    private IntegerProperty disChangeCount;
    private ObjectProperty<LocalDate> lastDate;
    private StringProperty code;
    private StringProperty condition;
    private StringProperty descr;
    private StringProperty groupBattery;
    private StringProperty status;

    public BatteryFXDTO(Integer id, Integer changeCount,
                        Integer cycles, Integer disChangeCount,
                        LocalDate lastDate, String code,
                        String condition, String descr,
                        String groupBattery, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.changeCount = new SimpleIntegerProperty(changeCount);
        this.cycles = new SimpleIntegerProperty(cycles);
        this.disChangeCount = new SimpleIntegerProperty(disChangeCount);
        this.lastDate = new SimpleObjectProperty<>(lastDate);
        this.code = new SimpleStringProperty(code);
        this.condition = new SimpleStringProperty(condition);
        this.descr = new SimpleStringProperty(descr);
        this.groupBattery = new SimpleStringProperty(groupBattery);
        this.status = new SimpleStringProperty(status);
    }
}
