package ru.popov.logssystemapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Setter
@Getter
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "zav_number")
    private String zavNumber;
    @Temporal(TemporalType.DATE)
    private LocalDate dateReceipt;
    private String organization;
    private String equipment;
    @Column(name = "br_number")
    private String brNumber;
    private String chexol;
    private String changer;
    private String battery;

    public Journal(){}

    public Journal(String name, String zavNumber, LocalDate dateReceipt,
                   String organization, String equipment, String brNumber,
                   String chexol, String changer, String battery) {
        this.name = name;
        this.zavNumber = zavNumber;
        this.dateReceipt = dateReceipt;
        this.organization = organization;
        this.equipment = equipment;
        this.brNumber = brNumber;
        this.chexol = chexol;
        this.changer = changer;
        this.battery = battery;
    }


    @Override
    public String toString() {
        return id + " " + name + " " +  zavNumber + " " + dateReceipt + " " +
                organization + " " +  equipment + " " + brNumber  + " "
                +  chexol + " " + changer + " " +  battery;
    }
}
