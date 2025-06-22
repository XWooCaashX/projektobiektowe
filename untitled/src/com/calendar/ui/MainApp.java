package com.calendar.ui;

import com.calendar.exception.StorageException;
import com.calendar.model.Event;
import com.calendar.model.Meeting;
import com.calendar.model.Reminder;
import com.calendar.service.EventStorage;
import com.calendar.service.FileEventStorage;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Główna klasa interfejsu graficznego (GUI) aplikacji.
 */
public class MainApp {
    private JFrame frame;
    private DefaultListModel<Event> eventListModel;
    private JList<Event> eventList;
    private EventStorage eventStorage;
    private List<Event> events;
    private CalendarPanel calendarPanel;
    private LocalDate selectedDate;


    public MainApp() {
        eventStorage = new FileEventStorage("events.txt");
        try {
            events = eventStorage.wczytajWydarzenia();
        } catch (StorageException e) {
            events = new ArrayList<>();
            JOptionPane.showMessageDialog(null, "Błąd wczytywania wydarzeń: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        selectedDate = LocalDate.now();
        inicjalizuj();
    }

    /**
     * Inicjalizacja komponentów GUI.
     */
    private void inicjalizuj() {
        frame = new JFrame("Kalendarz Wydarzeń");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        eventListModel = new DefaultListModel<>();
        eventList = new JList<>(eventListModel);
        JScrollPane eventScrollPane = new JScrollPane(eventList);

        calendarPanel = new CalendarPanel(events, date -> {
            selectedDate = date;
            aktualizujListeWydarzenDlaWybranejDaty();
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, calendarPanel, eventScrollPane);
        splitPane.setDividerLocation(450);
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);


        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        JButton btnAddMeeting = new JButton("Dodaj Spotkanie");
        btnAddMeeting.addActionListener(e -> dodajSpotkanie());
        panel.add(btnAddMeeting);

        JButton btnAddReminder = new JButton("Dodaj Przypomnienie");
        btnAddReminder.addActionListener(e -> dodajPrzypomnienie());
        panel.add(btnAddReminder);

        JButton btnDelete = new JButton("Usuń Zaznaczone");
        btnDelete.addActionListener(e -> usunZaznaczoneWydarzenie());
        panel.add(btnDelete);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                zapiszWydarzenia();
            }
        });

        aktualizujListeWydarzenDlaWybranejDaty();
    }

    private void aktualizujListeWydarzenDlaWybranejDaty() {
        eventListModel.clear();
        if (selectedDate != null) {
            List<Event> dailyEvents = events.stream()
                    .filter(event -> event.pobierzDate().equals(selectedDate))
                    .collect(Collectors.toList());
            dailyEvents.forEach(eventListModel::addElement);
        }
    }


    /**
     * Logika dodawania spotkania.
     */
    private void dodajSpotkanie() {
        JTextField descriptionField = new JTextField();
        JTextField locationField = new JTextField();
        // Użyj wybranej daty z kalendarza
        JTextField dateField = new JTextField(selectedDate.toString());
        dateField.setEditable(false); // Data jest wybrana z kalendarza

        Object[] message = {
            "Opis:", descriptionField,
            "Miejsce:", locationField,
            "Data:", dateField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Dodaj Spotkanie", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                LocalDate date = LocalDate.parse(dateField.getText());
                Meeting meeting = new Meeting(date, descriptionField.getText(), locationField.getText());
                events.add(meeting);
                aktualizujListeWydarzenDlaWybranejDaty();
                calendarPanel.ustawWydarzenia(events); // Zaktualizuj kalendarz, aby pokazać nowy event
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy format danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Logika dodawania przypomnienia.
     */
    private void dodajPrzypomnienie() {
        JTextField descriptionField = new JTextField();
        JCheckBox highPriorityCheckBox = new JCheckBox("Wysoki priorytet");
        JTextField dateField = new JTextField(selectedDate.toString());
        dateField.setEditable(false);

        Object[] message = {
            "Opis:", descriptionField,
            "Data:", dateField,
            highPriorityCheckBox
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Dodaj Przypomnienie", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                LocalDate date = LocalDate.parse(dateField.getText());
                Reminder reminder = new Reminder(date, descriptionField.getText(), highPriorityCheckBox.isSelected());
                events.add(reminder);
                aktualizujListeWydarzenDlaWybranejDaty();
                calendarPanel.ustawWydarzenia(events);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy format danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Logika usuwania wydarzenia.
     */
    private void usunZaznaczoneWydarzenie() {
        Event selectedEvent = eventList.getSelectedValue();
        if (selectedEvent != null) {
            events.remove(selectedEvent);
            aktualizujListeWydarzenDlaWybranejDaty();
            calendarPanel.ustawWydarzenia(events);
        } else {
            JOptionPane.showMessageDialog(frame, "Proszę zaznaczyć wydarzenie do usunięcia.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Zapisuje wydarzenia przy zamykaniu aplikacji.
     */
    private void zapiszWydarzenia() {
        try {
            eventStorage.zapiszWydarzenia(events);
        } catch (StorageException e) {
            JOptionPane.showMessageDialog(frame, "Błąd zapisu wydarzeń: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void pokaz() {
        frame.setVisible(true);
    }
}
