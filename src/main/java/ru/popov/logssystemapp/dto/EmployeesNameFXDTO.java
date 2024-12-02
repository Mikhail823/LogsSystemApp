package ru.popov.logssystemapp.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeesNameFXDTO {
    private StringProperty name;

    public EmployeesNameFXDTO(String name){
        this.name = new SimpleStringProperty(name);
    }

    @Override
    public String toString() {
        return name.getValue();
    }
}
