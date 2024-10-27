package com.scm.proj.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.proj.spring.entity.User;
import java.lang.String;
import java.util.Optional;
import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User, String>{

    Optional<User> findByEmail(String email);

    User getById(String id);

    Optional<User> findByEmailAndPassword(String email, String password);
}

