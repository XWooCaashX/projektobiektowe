package com.calendar.service;

import com.calendar.exception.StorageException;
import com.calendar.model.Event;

import java.util.List;

/**
 * Interfejs definiujący operacje zapisu i odczytu wydarzeń.
 * Użycie interfejsu pozwala na łatwą zmianę sposobu przechowywania danych.
 */
public interface EventStorage {
    /**
     * Zapisuje listę wydarzeń.
     * @param events Lista wydarzeń do zapisania.
     * @throws StorageException gdy wystąpi błąd zapisu.
     */
    void zapiszWydarzenia(List<Event> events) throws StorageException;

    /**
     * Wczytuje listę wydarzeń.
     * @return Lista wczytanych wydarzeń.
     * @throws StorageException gdy wystąpi błąd odczytu.
     */
    List<Event> wczytajWydarzenia() throws StorageException;
}
