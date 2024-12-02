package ru.popov.logssystemapp.dto;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class JournalFXDTO {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty zavNumber;
    private ObjectProperty<LocalDate> dateReceipt;
    private StringProperty organization;
    private StringProperty equipment;
    private StringProperty brNumber;
    private StringProperty chexol;
    private StringProperty changer;
    private StringProperty battery;

    public JournalFXDTO(Integer id, String name,
                        String zavNumber,
                        LocalDate dateReceipt,
                        String organization,
                        String equipment,
                        String brNumber,
                        String chexol,
                        String changer,
                        String battery) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.zavNumber = new SimpleStringProperty(zavNumber);
        this.dateReceipt = new SimpleObjectProperty<>(dateReceipt);
        this.organization = new SimpleStringProperty(organization);
        this.equipment = new SimpleStringProperty(equipment);
        this.brNumber = new SimpleStringProperty(brNumber);
        this.chexol = new SimpleStringProperty(chexol);
        this.changer = new SimpleStringProperty(changer);
        this.battery = new SimpleStringProperty(battery);
    }
}
