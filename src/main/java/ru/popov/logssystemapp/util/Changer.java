package ru.popov.logssystemapp.util;

public enum Changer {
    CD("CD-30"),
    NB("НБЗУ-1"),
    NBU("НБЗУ-1У"),
    VAC("VAC-6010");

    private String nameChanger;

    Changer(String nameChanger) {
        this.nameChanger = nameChanger;
    }

    public String getNameChanger() {
        return nameChanger;
    }

    @Override
    public String toString() {
        return nameChanger;
    }
}
