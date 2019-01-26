package com.ztp.notes.service;

import com.ztp.notes.model.User;

public interface UserService {
    User loginUser(User user);
    User getUser(String username);
    void saveUser(User user);
}
