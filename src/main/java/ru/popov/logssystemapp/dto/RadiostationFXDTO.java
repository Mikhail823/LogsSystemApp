package ru.popov.logssystemapp.dto;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RadiostationFXDTO {

    private IntegerProperty id;
    private StringProperty brNumber;
    private StringProperty zavNumber;
    private StringProperty indexRadio;
    private StringProperty garnitura;
    private BooleanProperty isChexol;

    public RadiostationFXDTO(Integer id, String brNumber, String zavNumber,
                             String indexRadio, String garnitura, Boolean isChexol) {
        this.id = new SimpleIntegerProperty(id);
        this.brNumber = new SimpleStringProperty(brNumber);
        this.zavNumber = new SimpleStringProperty(zavNumber);
        this.indexRadio = new SimpleStringProperty(indexRadio);
        this.garnitura = new SimpleStringProperty(garnitura);
        this.isChexol = new SimpleBooleanProperty(isChexol);
    }
}
