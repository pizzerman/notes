package com.ztp.notes.service;

import com.ztp.notes.model.Censorship;
import com.ztp.notes.model.Note;

import java.util.List;

public interface CensorshipService {
    Censorship getCensorship(String username, String phrase);
    List<Censorship> getUserCensorship(String username);
    void saveCensorship(String username, Censorship censorship);
    void deleteCensorship(String username, String phrase);
    void censorNoteText(Note note);
}
