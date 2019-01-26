package com.ztp.notes.exception;

public class NotesException extends RuntimeException {

    public NotesException(String message) {
        super(message);
    }

    public NotesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotesException(Throwable cause) {
        super(cause);
    }
}
