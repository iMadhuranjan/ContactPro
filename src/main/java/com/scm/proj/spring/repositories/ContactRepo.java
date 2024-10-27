package com.scm.proj.spring.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.scm.proj.spring.entity.Contact;
import com.scm.proj.spring.entity.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String>{

    //Finc Contact of User
    List<Contact> findByUser(User user);

    @Query("SELECT c from Contact c where c.user.userId= :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    Page<Contact> findByUser(User user, PageRequest pagable);

    @Query("SELECT COUNT(u) FROM Contact u")
    Long countUsers();

}   

