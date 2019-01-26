package com.ztp.notes.service;

import com.ztp.notes.dao.CensorshipRepository;
import com.ztp.notes.dao.UserRepository;
import com.ztp.notes.exception.NotesException;
import com.ztp.notes.model.Censorship;
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

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CensorshipServiceImplTest {

    @TestConfiguration
    static class CensorshipServiceImplTestContextConfiguration {

        @Bean
        public CensorshipService censorshipService() {
            return new CensorshipServiceImpl();
        }
    }

    @Autowired
    private CensorshipService censorshipService;

    @MockBean
    private CensorshipRepository censorshipRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        List<Censorship> censorshipList = Arrays.asList(
                new Censorship("firstPhrase"),
                new Censorship("secondPhrase"),
                new Censorship("thirdPhrase")
        );
        Censorship censorship = new Censorship("phrase");
        User user = new User("username", "password", true);
        Note note = new Note("note", LocalDate.of(2018,12,31),
                "Phrases, phrase. Phrase aphrase aphrasea phrasea. phrase");
        censorship.setUsers(Collections.singletonList(user));
        user.setCensorship(Collections.singletonList(censorship));
        note.setUser(user);
        user.setNotes(Collections.singletonList(note));

        Mockito.when(censorshipRepository.findById(censorship.getCensoredPhrase()))
                .thenReturn(Optional.of(censorship));
        Mockito.when(censorshipRepository.findByUsers_Username(user.getUsername()))
                .thenReturn(censorshipList);
        Mockito.when(userRepository.findById(user.getUsername()))
                .thenReturn(Optional.of(user));
    }

    @Test
    public void whenValidUsernameAndPhrase_thenCensorshipShouldBeFound() {
        String username = "username";
        String phrase = "phrase";

        Censorship found = censorshipService.getCensorship(username, phrase);

        assertThat(found.getCensoredPhrase()).isEqualTo(phrase);
    }

    @Test
    public void whenValidUsername_thenCensorshipListShouldBeFound() {
        String username = "username";
        List<Censorship> censorshipList = Arrays.asList(
                new Censorship("firstPhrase"),
                new Censorship("secondPhrase"),
                new Censorship("thirdPhrase")
        );
        List<Censorship> found = censorshipService.getUserCensorship(username);

        Assert.assertEquals(found, censorshipList);
        Assert.assertNotSame(found, censorshipList);
    }

    @Test
    public void whenNoteTextContainsPhrases_thenNoteTextShoudBeCensored() {
        String censoredText = "Phrases, p****e. p****e aphrase aphrasea phrasea. p****e";
        Censorship censorship = new Censorship("phrase");
        User user = new User("username", "password", true);
        Note note = new Note("note", LocalDate.of(2018,12,31),
                "Phrases, phrase. Phrase aphrase aphrasea phrasea. phrase");
        censorship.setUsers(Collections.singletonList(user));
        user.setCensorship(Collections.singletonList(censorship));
        note.setUser(user);
        user.setNotes(Collections.singletonList(note));
        censorshipService.censorNoteText(note);

        assertThat(note.getText()).isEqualTo(censoredText);
    }

    @Test(expected = NotesException.class)
    public void whenPhraseContainsNumber_thenExceptionIsThrown() {
        Censorship censorship = new Censorship("5");
        censorshipService.saveCensorship("username", censorship);
    }
}
