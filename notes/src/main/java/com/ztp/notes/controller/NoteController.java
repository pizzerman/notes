package com.ztp.notes.controller;

import com.ztp.notes.model.Note;
import com.ztp.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes/{username}")
    public List<Note> getNotes(@PathVariable String username) {
        return noteService.getNotes(username);
    }

    @PostMapping("/notes/{username}")
    public List<Note> getNotesByParams(
            @RequestBody Map<String, String> params,
            @PathVariable String username) {
        return noteService.getNotesByParams(username, params);
    }

    @GetMapping("/notes/{noteId}/{censored}")
    public Note getNote(@PathVariable int noteId, @PathVariable boolean censored) {
        return noteService.getNote(noteId, censored);
    }

    @PostMapping("/notes")
    public Note saveNote(@RequestBody Note note) {
        noteService.saveNote(note);
        return note;
    }

    @DeleteMapping("/notes/{noteId}")
    public void deleteNote(@PathVariable int noteId) {
        noteService.deleteNote(noteId);
    }
}
