package ru.popov.logssystemapp.service.impl;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import lombok.Getter;

@Getter
public class TaskApp extends Task<Void> {
    private String name;
    private String number;
    private String caseRadio;
    private String changer;
    private ProgressBar progressBar;


    public TaskApp(String name, String number, String caseRadio, String changer, ProgressBar progressBar) {
        this.name = name;
        this.number = number;
        this.caseRadio = caseRadio;
        this.changer = changer;
        this.progressBar = progressBar;
    }

    @Override
    protected Void call() throws Exception {
        return null;
    }
}
