package com.ztp.notes.service;

import com.ztp.notes.dao.CensorshipRepository;
import com.ztp.notes.exception.NotesException;
import com.ztp.notes.model.Censorship;
import com.ztp.notes.model.Note;
import com.ztp.notes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CensorshipServiceImpl implements CensorshipService {

    @Autowired
    private CensorshipRepository censorshipRepository;

    @Autowired
    private UserService userService;

    @Override
    public Censorship getCensorship(String username, String phrase) {
        Censorship censorship = censorshipRepository.findById(phrase).orElse(null);
        if(censorship != null) {
            for(User user : censorship.getUsers()) {
                if (user.getUsername().equals(username)) {
                    return censorship;
                }
            }
        }
        return null;
    }

    @Override
    public List<Censorship> getUserCensorship(String username) {
        User user = userService.getUser(username);
        return user.getCensorship();
    }

    @Override
    public void saveCensorship(String username, Censorship censorship) {
        if(censorship.getCensoredPhrase().trim().isEmpty()) {
            throw new NotesException("Zwrot nie może być pusty/dłuższy niż 45 znaków.");
        }
        if(censorship.getCensoredPhrase().matches(".*\\d+.*")) {
            throw new NotesException("Zwrot nie może zawierać cyfr.");
        }
        User user = userService.getUser(username);
        for(Censorship c : user.getCensorship()) {
            if(c.getCensoredPhrase().equals(censorship.getCensoredPhrase())) {
                throw new NotesException("Zwrot już istnieje na liście.");
            }
        }
        censorship.addUser(user);
        censorshipRepository.save(censorship);
    }

    @Override
    public void deleteCensorship(String username, String phrase) {
        User user = userService.getUser(username);
        Censorship censorship = getCensorship(username, phrase);
        if(censorship != null) {
            censorship.removeUser(user);
            censorshipRepository.save(censorship);

            if (censorship.getUsers().isEmpty()) {
                censorshipRepository.deleteById(phrase);
            }
        }
    }

    @Override
    public void censorNoteText(Note note) {
        User user = userService.getUser(note.getUser().getUsername());
        List<Censorship> censorship = user.getCensorship();
        Map<String, String> phrases = new HashMap<>();
        for(Censorship censor : censorship) {
            phrases.put(
                    censor.getCensoredPhrase(),
                    censorPhrase(censor.getCensoredPhrase())
            );
        }
        for(Map.Entry<String, String> entry : phrases.entrySet()) {
            note.setText(note.getText().replaceAll(
                    "(?i)\\b" + entry.getKey() + "\\b",
                    entry.getValue())
            );
        }
    }

    private String censorPhrase(String phrase) {
        String[] words = phrase.split(" ");
        StringBuilder censoredPhrase = new StringBuilder();
        for(String word : words) {
            char[] wordChars = word.toCharArray();
            if(word.length() < 3) {
                wordChars[0] = '*';
            } else {
                for (int i = 1; i < wordChars.length - 1; i++) {
                    wordChars[i] = '*';
                }
            }
            censoredPhrase.append(wordChars).append(" ");
        }
        censoredPhrase.delete(censoredPhrase.length() - 1, censoredPhrase.length());

        return censoredPhrase.toString();
    }
}
