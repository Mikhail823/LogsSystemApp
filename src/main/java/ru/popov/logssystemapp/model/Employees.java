package ru.popov.logssystemapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@Table(name = "employees")
@ToString
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_empolee")
    private String name;

    private String organ;


    public Employees (){}

    public Employees(String name, String organ) {
        this.name = name;
        this.organ = organ;
    }
}
