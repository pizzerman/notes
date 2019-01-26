package com.ztp.notes.dao;

import com.ztp.notes.model.Authority;
import com.ztp.notes.model.AuthorityId;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, AuthorityId> {}