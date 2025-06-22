package com.calendar;

import com.calendar.ui.MainApp;
import javax.swing.SwingUtilities;

/**
 * Główna klasa uruchomieniowa aplikacji.
 */
public class Main {
    /**
     * Metoda main, punkt startowy programu.
     * @param args argumenty wiersza poleceń (nieużywane).
     */
    public static void main(String[] args) {
        // Uruchomienie GUI w wątku dystrybucji zdarzeń Swing.
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.pokaz();
        });
    }
}
