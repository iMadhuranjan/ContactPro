package com.scm.proj.spring.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.proj.spring.entity.Contact;
import com.scm.proj.spring.entity.User;

public interface ContactService {

    Contact save(Contact contact);

    Contact upadte(Contact contact);

    List<Contact> getAll();

    Contact getById(String id);

    // Void deleteById(String id);

    void delete(String id);

    // List<Contact> Search(String name, String email, String phoneNumber);

    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user, int page, int size, String sortedBy, String direction);
   
}
