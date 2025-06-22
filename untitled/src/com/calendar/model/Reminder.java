package com.calendar.model;

import java.time.LocalDate;

/**
 * Klasa reprezentująca przypomnienie, dziedziczy po Event.
 */
public class Reminder extends Event {
    private boolean isHighPriority;

    public Reminder(LocalDate date, String description, boolean isHighPriority) {
        super(date, description);
        this.isHighPriority = isHighPriority;
    }

    public boolean czyWysokiPriorytet() {
        return isHighPriority;
    }

    public void ustawWysokiPriorytet(boolean highPriority) {
        isHighPriority = highPriority;
    }

    /**
     * Polimorficzna implementacja metody toString.
     */
    @Override
    public String toString() {
        return "Przypomnienie: " + super.pobierzDate() + " - " + super.pobierzOpis() + (isHighPriority ? " [Wysoki priorytet]" : "");
    }

    /**
     * Implementacja formatu pliku dla przypomnienia.
     */
    @Override
    public String formatDoPliku() {
        return "REMINDER;" + pobierzDate() + ";" + pobierzOpis() + ";" + isHighPriority;
    }
}
