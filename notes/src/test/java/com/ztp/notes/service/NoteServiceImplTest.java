package com.ztp.notes.service;

import com.ztp.notes.dao.NoteRepository;
import com.ztp.notes.dao.UserRepository;
import com.ztp.notes.exception.NotesException;
import com.ztp.notes.model.Note;
import com.ztp.notes.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceImplTest {

    @TestConfiguration
    static class NoteServiceImplTestContextConfiguration {

        @Bean
        public NoteService noteService() {
            return new NoteServiceImpl();
        }
    }

    @Autowired
    private NoteService noteService;

    @MockBean
    private NoteRepository noteRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        Note note1 = new Note("note1", LocalDate.of(2018,12,31), "Test1");
        note1.setId(2);
        Note note2 = new Note("note2", LocalDate.of(2018,12,31), "Test2");
        note2.setId(3);
        List<Note> notes = Arrays.asList(note1, note2);
        User user = new User("username", "password", true);
        Note note = new Note("note", LocalDate.of(2018,12,30), "Text");
        note.setId(1);

        Mockito.when(noteRepository.findById(note.getId()))
                .thenReturn(Optional.of(note));
        Mockito.when(userRepository.findById(user.getUsername()))
                .thenReturn(Optional.of(user));
        Mockito.when(noteRepository.findByUser(user))
                .thenReturn(notes);
        Mockito.when(noteRepository.findByUserAndDate(user, LocalDate.of(2018,12,30)))
                .thenReturn(Collections.singletonList(note));
        Mockito.when(noteRepository.findByUserAndTitleIgnoreCaseContaining(user, "note"))
                .thenReturn(notes);
    }

    @Test
    public void whenValidId_thenNoteShouldBeFound() {
        int id = 1;
        Note found = noteService.getNote(id, false);

        assertThat(found.getId()).isEqualTo(id);
    }

    @Test
    public void whenValidUsername_thenUserNotesShouldBeFound() {
        Note note1 = new Note("note1", LocalDate.of(2018,12,31), "Test1");
        note1.setId(2);
        Note note2 = new Note("note2", LocalDate.of(2018,12,31), "Test2");
        note2.setId(3);
        List<Note> notes = Arrays.asList(note1, note2);

        List<Note> found = noteService.getNotes("username");

        Assert.assertEquals(found, notes);
        Assert.assertNotSame(found, notes);
    }

    @Test
    public void whenValidUserAndDate_thenNotesByUserAndDateShouldBeFound() {
        Note note1 = new Note("note1", LocalDate.of(2018,12,31), "Test1");
        note1.setId(2);
        Note note = new Note("note", LocalDate.of(2018,12,30), "Text");
        note.setId(1);

        List<Note> note1List = Collections.singletonList(note1);
        List<Note> noteList = Collections.singletonList(note);

        List<Note> found = noteService.getNotesByParams(
                "username",
                new HashMap<String, String>(){{
                    put("date", "20181230");
                }});

        Assert.assertEquals(found, noteList);
        Assert.assertNotEquals(found, note1List);
        Assert.assertNotSame(found, noteList);
    }

    @Test
    public void whenValidUserAndTitle_thenNotesByUserAndTitleShouldBeFound() {
        Note note2 = new Note("title", LocalDate.of(2018,12,30), "Test");
        note2.setId(1);
        Note note1 = new Note("note1", LocalDate.of(2018,12,31), "Test1");
        note1.setId(2);
        Note note = new Note("note2", LocalDate.of(2018,12,31), "Test2");
        note.setId(3);

        List<Note> note2List = Arrays.asList(note, note1, note2);
        List<Note> noteList = Arrays.asList(note1, note);

        List<Note> found = noteService.getNotesByParams(
                "username",
                new HashMap<String, String>(){{
                    put("title", "note");
                }});

        Assert.assertEquals(found, noteList);
        Assert.assertNotEquals(found, note2List);
        Assert.assertNotSame(found, noteList);
    }

    @Test(expected = NotesException.class)
    public void whenDateIsAfter_thenExceptionIsThrown() {
        Note note = new Note("note", LocalDate.of(2043, 1, 1), "text");
        noteService.saveNote(note);
    }

    @Test(expected = NotesException.class)
    public void whenDateIsBehind_thenExceptionIsThrown() {
        Note note = new Note("note", LocalDate.of(1979, 12, 31), "text");
        noteService.saveNote(note);
    }
}
