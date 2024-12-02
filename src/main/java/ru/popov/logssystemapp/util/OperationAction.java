package ru.popov.logssystemapp.util;

public enum OperationAction {
    ON_A_CHANGE("На заряд"),
    ON_DISCHANGE("На разряд"),
    ON_THE_ISSUE("На выдаче");

    private String action;

    OperationAction(String action){
        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }
}
