package com.calendar.exception;

/**
 * Własna klasa wyjątku do obsługi błędów związanych z zapisem/odczytem.
 */
public class StorageException extends Exception {
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
