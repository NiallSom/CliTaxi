package com.ise.taxiapp.entities;

//public static final String ANSI_RED = "\u001B[31m";
//public static final String ANSI_GREEN = "\u001B[32m";
//public static final String RESET ="\033[0m";

public enum TaxiStatus {
    AVAILABLE("\u001B[31m"),
    EN_ROUTE("\u001B[32m"),
    BUSY("\033[0m");

    private final String asciiColour;

    TaxiStatus(String asciiColour) {
        this.asciiColour = asciiColour;
    }

    public String getAsciiColour() {
        return asciiColour;
    }
}
