package com.calendar.model;

import java.time.LocalDate;

/**
 * Abstrakcyjna klasa bazowa dla wszystkich wydarzeń.
 * Definiuje wspólne właściwości i zachowania.
 */
public abstract class Event {
    private LocalDate date;
    private String description;

    public Event(LocalDate date, String description) {
        this.date = date;
        this.description = description;
    }

    // Gettery i Settery
    public LocalDate pobierzDate() {
        return date;
    }

    public void ustawDate(LocalDate date) {
        this.date = date;
    }

    public String pobierzOpis() {
        return description;
    }

    public void ustawOpis(String description) {
        this.description = description;
    }

    /**
     * Metoda abstrakcyjna do reprezentacji tekstowej wydarzenia.
     * Każda klasa pochodna musi ją zaimplementować.
     * @return String reprezentujący wydarzenie.
     */
    @Override
    public abstract String toString();

    /**
     * Metoda abstrakcyjna do formatowania danych do zapisu w pliku.
     * @return String w formacie do zapisu.
     */
    public abstract String formatDoPliku();
}
