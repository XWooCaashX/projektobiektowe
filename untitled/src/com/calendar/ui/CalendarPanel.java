package com.calendar.ui;

import com.calendar.model.Event;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Panel wyświetlający kalendarz miesięczny, umożliwiający nawigację i wybór daty.
 */
public class CalendarPanel extends JPanel {

    private YearMonth currentMonth;
    private LocalDate selectedDate;
    private List<Event> events;
    private final Consumer<LocalDate> onDateSelected;

    private final JLabel monthLabel;
    private final JPanel daysPanel;

    private final Color eventDayColor = new Color(173, 216, 230); // Jasnoniebieski
    private final Color todayColor = new Color(255, 228, 181); // Mokasyn
    private final Color selectedColor = new Color(144, 238, 144); // Jasnozielony
    private final Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 2);

    /**
     * Konstruktor panelu kalendarza.
     * @param events Lista wydarzeń do wyświetlenia w kalendarzu.
     * @param onDateSelected Akcja wykonywana po wybraniu daty.
     */
    public CalendarPanel(List<Event> events, Consumer<LocalDate> onDateSelected) {
        this.events = events;
        this.onDateSelected = onDateSelected;
        this.currentMonth = YearMonth.now();
        this.selectedDate = LocalDate.now();

        setLayout(new BorderLayout());

        // Nagłówek z nawigacją
        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> zmienMiesiac(-1));
        headerPanel.add(prevButton, BorderLayout.WEST);

        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(monthLabel, BorderLayout.CENTER);

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> zmienMiesiac(1));
        headerPanel.add(nextButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Siatka dni
        daysPanel = new JPanel(new GridLayout(0, 7));
        add(daysPanel, BorderLayout.CENTER);

        aktualizujKalendarz();
    }

    /**
     * Ustawia wydarzenia i odświeża widok kalendarza.
     * @param events Lista wydarzeń.
     */
    public void ustawWydarzenia(List<Event> events) {
        this.events = events;
        aktualizujKalendarz();
    }

    /**
     * Zmienia bieżący miesiąc.
     * @param amount Liczba miesięcy do dodania (wartość ujemna dla poprzednich miesięcy).
     */
    private void zmienMiesiac(int amount) {
        currentMonth = currentMonth.plusMonths(amount);
        aktualizujKalendarz();
    }

    /**
     * Aktualizuje widok kalendarza, odświeżając dni i etykietę miesiąca.
     */
    private void aktualizujKalendarz() {
        daysPanel.removeAll();
        monthLabel.setText(currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.getYear());

        // Nagłówki dni tygodnia
        for (int i = 0; i < 7; i++) {
            DayOfWeek dayOfWeek = DayOfWeek.MONDAY.plus(i);
            JLabel dayLabel = new JLabel(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()), SwingConstants.CENTER);
            daysPanel.add(dayLabel);
        }

        LocalDate firstOfMonth = currentMonth.atDay(1);
        int dayOfWeekOfFirst = firstOfMonth.getDayOfWeek().getValue(); // 1 dla poniedziałku, 7 dla niedzieli

        // Dodaj puste panele dla dni przed pierwszym dniem miesiąca
        for (int i = 1; i < dayOfWeekOfFirst; i++) {
            daysPanel.add(new JPanel());
        }

        List<LocalDate> eventDates = events.stream().map(Event::pobierzDate).distinct().collect(Collectors.toList());

        // Dodaj przyciski dni
        for (int day = 1; day <= currentMonth.lengthOfMonth(); day++) {
            LocalDate date = currentMonth.atDay(day);
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setOpaque(true);
            dayButton.setBackground(Color.WHITE);
            dayButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            if (date.equals(LocalDate.now())) {
                dayButton.setBackground(todayColor);
            }
            if (eventDates.contains(date)) {
                dayButton.setBackground(eventDayColor);
            }
            if (date.equals(selectedDate)) {
                dayButton.setBorder(selectedBorder);
                dayButton.setBackground(selectedColor);
            }

            dayButton.addActionListener(e -> {
                selectedDate = date;
                onDateSelected.accept(date);
                aktualizujKalendarz(); // Odśwież, aby pokazać nowy wybór
            });
            daysPanel.add(dayButton);
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }
}