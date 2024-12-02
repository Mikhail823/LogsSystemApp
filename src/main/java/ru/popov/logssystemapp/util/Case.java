package ru.popov.logssystemapp.util;

public enum Case {
    WITH_A_CASE("с чехлом"),
    WITHOUT_A_COVER("без чехла");

    private String str;

    Case(String str){
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
