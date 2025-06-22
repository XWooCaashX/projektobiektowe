package com.calendar.model;

import java.time.LocalDate;

/**
 * Klasa reprezentująca spotkanie, dziedziczy po Event.
 */
public class Meeting extends Event {
    private String location;

    public Meeting(LocalDate date, String description, String location) {
        super(date, description);
        this.location = location;
    }

    public String pobierzLokalizacje() {
        return location;
    }

    public void ustawLokalizacje(String location) {
        this.location = location;
    }

    /**
     * Polimorficzna implementacja metody toString.
     */
    @Override
    public String toString() {
        return "Spotkanie: " + super.pobierzDate() + " - " + super.pobierzOpis() + " (Miejsce: " + location + ")";
    }

    /**
     * Implementacja formatu pliku dla spotkania.
     */
    @Override
    public String formatDoPliku() {
        return "MEETING;" + pobierzDate() + ";" + pobierzOpis() + ";" + location;
    }
}
