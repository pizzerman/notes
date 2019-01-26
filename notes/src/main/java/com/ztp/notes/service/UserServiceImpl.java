package com.ztp.notes.service;

import com.ztp.notes.dao.UserRepository;
import com.ztp.notes.exception.NotesException;
import com.ztp.notes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User loginUser(User user) {
        User u = userRepository.findById(user.getUsername()).orElse(null);
        if (u == null || !passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            throw new NotesException("Zła nazwa użytkownika lub hasło.");
        }
        return u;
    }

    @Override
    public User getUser(String username) {
        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            throw new NotesException("Nie znaleziono użytkownika o podanej nazwie.");
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        if (user.getUsername().trim().isEmpty() || user.getPassword().trim().isEmpty()) {
            throw new NotesException("Nazwa użytkownika/hasło nie może być pusta.");
        }
        if (user.getUsername().contains(" ")) {
            throw new NotesException("Nazwa użytkownika nie może zawierać znaków białych..");
        }
        if (user.getPassword().contains(" ")) {
            throw new NotesException("Hasło nie może zawierać znaków białych.");
        }
        if (userRepository.existsById(user.getUsername())) {
            throw new NotesException("Nazwa użytkownika zajęta.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
