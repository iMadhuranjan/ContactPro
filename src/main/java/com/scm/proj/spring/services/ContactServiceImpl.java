package com.scm.proj.spring.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.proj.spring.entity.Contact;
import com.scm.proj.spring.entity.User;
import com.scm.proj.spring.helper.ResourcesNotFoundException;
import com.scm.proj.spring.repositories.ContactRepo;


@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;


    @Override
    public Contact save(Contact contact) {
       String ContactId= UUID.randomUUID().toString();
       contact.setId(ContactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact upadte(Contact contact) {
       var Olduser= contactRepo.findById(contact.getId())
       .orElseThrow(()-> new ResourcesNotFoundException("User Not Found"));
        Olduser.setName(contact.getName());
        Olduser.setAddress(contact.getAddress());
        Olduser.setDescription(contact.getDescription());
        Olduser.setEmail(contact.getEmail());
        Olduser.setFavourate(contact.isFavourate());
        Olduser.setLinkedinLink(contact.getLinkedinLink());
        Olduser.setPhoneNumber(contact.getPhoneNumber());
        Olduser.setPicture(contact.getPicture());
        Olduser.setWebsteLink(contact.getWebsteLink());
        return contactRepo.save(Olduser);

    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
       return contactRepo.findById(id).orElseThrow(()-> new ResourcesNotFoundException("Contact Not Found With Given Id "+ id));
    }

    @Override
    public void delete(String id) {
        var del=contactRepo.findById(id).orElseThrow(()-> new ResourcesNotFoundException("Contact Not Found With Given ID"));
         contactRepo.delete(del);
    }



    @Override
    public List<Contact> getByUserId(String userId) {
       return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort=direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var Pagable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user,Pagable);
    }

  




}
