module ru.popov.logssystemapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.desktop;

    opens ru.popov.logssystemapp to javafx.fxml;
    opens ru.popov.logssystemapp.controllers;
    opens ru.popov.logssystemapp.dao;
    opens ru.popov.logssystemapp.dialog;
    opens ru.popov.logssystemapp.dto;
    opens ru.popov.logssystemapp.model;
    opens ru.popov.logssystemapp.service;
    opens ru.popov.logssystemapp.util;
    exports ru.popov.logssystemapp;
}