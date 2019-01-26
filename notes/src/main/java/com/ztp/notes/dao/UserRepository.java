package com.ztp.notes.dao;

import com.ztp.notes.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {}