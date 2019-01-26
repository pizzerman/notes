package com.ztp.notes.dao;

import com.ztp.notes.model.Censorship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CensorshipRepository extends CrudRepository<Censorship, String> {
    List<Censorship> findByUsers_Username(String username);
}