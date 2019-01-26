package com.ztp.notes.service;

import com.ztp.notes.model.Note;

import java.util.List;
import java.util.Map;

public interface NoteService {
    Note getNote(int id, boolean censored);
    List<Note> getNotes(String username);
    List<Note> getNotesByParams(String username, Map<String, String> params);
    void saveNote(Note note);
    void deleteNote(int id);
}
