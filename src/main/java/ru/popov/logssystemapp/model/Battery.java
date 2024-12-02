package ru.popov.logssystemapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table
public class Battery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer changeCount;
    private Integer cycles;
    private Integer disChangeCount;
    @Temporal(TemporalType.DATE)
    private LocalDate lastDate;
    private String code;
    private String condition;
    private String descr;
    private String groupBattery;
    private String status;
}
