package com.calendar.service;

import com.calendar.exception.StorageException;
import com.calendar.model.Event;
import com.calendar.model.Meeting;
import com.calendar.model.Reminder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja interfejsu EventStorage do obsługi zapisu/odczytu z pliku tekstowego.
 */
public class FileEventStorage implements EventStorage {
    private final String filePath;

    public FileEventStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Zapisuje wydarzenia do pliku.
     */
    @Override
    public void zapiszWydarzenia(List<Event> events) throws StorageException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Event event : events) {
                writer.write(event.formatDoPliku());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new StorageException("Błąd podczas zapisywania wydarzeń do pliku: " + filePath, e);
        }
    }

    /**
     * Wczytuje wydarzenia z pliku.
     */
    @Override
    public List<Event> wczytajWydarzenia() throws StorageException {
        List<Event> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 4);
                if (parts.length >= 3) {
                    String type = parts[0];
                    LocalDate date = LocalDate.parse(parts[1]);
                    String description = parts[2];

                    if ("MEETING".equals(type) && parts.length == 4) {
                        String location = parts[3];
                        events.add(new Meeting(date, description, location));
                    } else if ("REMINDER".equals(type) && parts.length == 4) {
                        boolean isHighPriority = Boolean.parseBoolean(parts[3]);
                        events.add(new Reminder(date, description, isHighPriority));
                    }
                }
            }
        } catch (IOException e) {
            // Ignorujemy błąd jeśli plik nie istnieje - przy pierwszym uruchomieniu
        } catch (Exception e) {
            throw new StorageException("Błąd podczas wczytywania wydarzeń z pliku: " + filePath, e);
        }
        return events;
    }
}
