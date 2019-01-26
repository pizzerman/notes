package com.ztp.notes.service;

import com.ztp.notes.dao.NoteRepository;
import com.ztp.notes.dao.UserRepository;
import com.ztp.notes.exception.NotesException;
import com.ztp.notes.model.Note;
import com.ztp.notes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CensorshipService censorshipService;

    @Override
    public Note getNote(int id, boolean censored) {
        Note note = noteRepository.findById(id).orElse(null);
        if(note != null && censored) {
            censorshipService.censorNoteText(note);
        }
        return note;
    }

    @Override
    public List<Note> getNotes(String username) {
        User user = userRepository.findById(username).orElse(null);
        return user.getNotes();
    }

    @Override
    public List<Note> getNotesByParams(String username, Map<String, String> params) {
        if(params.containsKey("date") && params.containsKey("title")) {
            return getNotesByDateAndTitle(username, params.get("date"), params.get("title"));
        } else if(params.containsKey("date") && !params.containsKey("title")) {
            return getNotesByDate(username, params.get("date"));
        } else {
            return getNotesByTitle(username, params.get("title"));
        }
    }

    private List<Note> getNotesByDate(String username, String date) {
        User user = userRepository.findById(username).orElse(null);
        List<Note> notes = null;
        LocalDate d = getDateFromString(date);
        if(user != null) {
            notes = noteRepository.findByUserAndDate(user, d);
        }
        return notes;
    }

    private List<Note> getNotesByTitle(String username, String title) {
        User user = userRepository.findById(username).orElse(null);
        List<Note> notes = null;
        String searchTitle = title.replaceAll(",", " ");
        if(user != null) {
            notes = noteRepository.findByUserAndTitleIgnoreCaseContaining(user, searchTitle);
        }
        return notes;
    }

    private List<Note> getNotesByDateAndTitle(String username, String date, String title) {
        User user = userRepository.findById(username).orElse(null);
        List<Note> notes = null;
        LocalDate d = getDateFromString(date);
        String searchTitle = title.replaceAll(",", " ");
        if(user != null) {
            notes = noteRepository.findByUserAndDateAndTitleIgnoreCaseContaining(user, d, searchTitle);
        }
        return notes;
    }

    @Override
    public void saveNote(Note note) {
        if(note.getDate() == null) {
            note.setDate(LocalDate.now(ZoneId.systemDefault()));
        } else {
            LocalDate date = note.getDate();
            if(date.isAfter(LocalDate.of(2042, 12, 31))) {
                throw new NotesException("Data nie może być późniejsza niż 2042-12-31.");
            }
            if(date.isBefore(LocalDate.of(1980, 1, 1))) {
                throw new NotesException("Data nie może być wcześniejsza niż 1980-01-01.");
            }
        }
        if(noteRepository.existsById(note.getId())) {
            User user = userRepository.findById(note.getUser().getUsername()).orElse(null);
            List<Note> userNotes = user.getNotes();
            for (Note n : userNotes) {
                if (n.getId() == note.getId()) {
                    n.setTitle(note.getTitle());
                    n.setDate(note.getDate());
                    n.setText(note.getText());
                    break;
                }
            }
            userRepository.save(user);
        } else {
            noteRepository.save(note);
        }
    }

    @Override
    public void deleteNote(int id) {
        noteRepository.deleteById(id);
    }

    private LocalDate getDateFromString(String date) {
        if(!date.matches("\\d{8}")) {
            throw new NotesException("Błędny format podanej daty.");
        }
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        return LocalDate.of(year, month, day);
    }
}
