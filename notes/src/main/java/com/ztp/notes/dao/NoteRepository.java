package com.ztp.notes.dao;

import com.ztp.notes.model.Note;
import com.ztp.notes.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Integer> {
    List<Note> findByUser(User user);
    List<Note> findByUserAndDate(User user, LocalDate date);
    List<Note> findByUserAndTitleIgnoreCaseContaining(User user, String title);
    List<Note> findByUserAndDateAndTitleIgnoreCaseContaining(User user, LocalDate date, String title);
}