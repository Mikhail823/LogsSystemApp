package ru.popov.logssystemapp.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeesFXDTO {
    private IntegerProperty id;

    private StringProperty name;

    private StringProperty organization;

    public EmployeesFXDTO(Integer id, String name, String organization){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.organization = new SimpleStringProperty(organization);
    }
}
