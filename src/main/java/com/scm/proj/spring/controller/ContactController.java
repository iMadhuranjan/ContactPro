package com.scm.proj.spring.controller;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.scm.proj.spring.entity.Contact;
import com.scm.proj.spring.entity.User;
import com.scm.proj.spring.form.ContactForm;
import com.scm.proj.spring.helper.Helper;
import com.scm.proj.spring.repositories.ContactRepo;
import com.scm.proj.spring.services.ContactService;
import com.scm.proj.spring.services.imageService;
import com.scm.proj.spring.services.userService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger= LoggerFactory.getLogger(ContactController.class);

    @Autowired
private ContactService contactService;

@Autowired
private imageService imageService;

@Autowired
private userService service;

@Autowired
private ContactRepo contactRepo;

    @GetMapping("/add")
    public String AddContacts(Model model){
        ContactForm contactForm= new ContactForm();
        model.addAttribute("contactForm", contactForm);
        // contactForm.setName("Madhu");
        return "user/addcontacts";
    }

    
    @PostMapping("/add")
    public String postMethodName(@Valid @ModelAttribute ContactForm contactForm, BindingResult  result,  Authentication authentication, HttpSession Session) {

        if(result.hasErrors()){
            return "user/addcontacts";
        };

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user= service.getUserByEmail(username);

        logger.info("File Information = {}",contactForm.getPicture().getOriginalFilename());

        
        // Image Service To Upload image or file 

        String filename = UUID.randomUUID().toString();

        String fileURL = imageService.uploadImage(contactForm.getPicture()) ;
        

        logger.info("Image File Name is = {}", fileURL);
        Contact contact=new Contact();
        contact.setName(contactForm.getName());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setFavourate(contactForm.isFavourate());
        contact.setWebsteLink(contactForm.getWebsteLink()); 
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setPicture(fileURL);
        contact.setUser(user);
        
        contactService.save(contact);

        Session.setAttribute("message", "Your Contact is Successfully Added");

        return "redirect:/user/contacts/add";
    }
    

    @GetMapping("")
    public String ViewContacts(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name= "size", defaultValue = "5") int size,
    @RequestParam(name ="sortBy", defaultValue = "name") String sortBy,
    @RequestParam(value = "direction", defaultValue="asc") String direction,

        Model model, Authentication authentication){
        String username=Helper.getEmailOfLoggedInUser(authentication);
        User user= service.getUserByEmail(username);
        // model.addAttribute("count", contactRepo.count());
        
         Page<Contact> Pagecontact=contactService.getByUser(user, page, size, sortBy, direction);
        // 
        //  logger.info("Total Contact us {}", contactRepo.count());
        model.addAttribute("pagecontact", Pagecontact);
        return "user/viewcontact";
    }

    @GetMapping("/delete/{id}")
    public String deleteConteact(@PathVariable String  id, HttpSession asec){
        contactService.delete(id);
        asec.setAttribute("message", "Your Contact is Deleted Successfully!");
        return "redirect:/user/contacts";
    }

    @GetMapping("/view/{id}")
    public String updateContactView(@PathVariable String id, Model modal){

        var Contact= contactService.getById(id);
        ContactForm contactform=new ContactForm();
        contactform.setName(Contact.getName());
        contactform.setAddress(Contact.getAddress()); 
        contactform.setDescription(Contact.getDescription());
        contactform.setEmail(Contact.getEmail());
        contactform.setWebsteLink(Contact.getWebsteLink());
        contactform.setPhoneNumber(Contact.getPhoneNumber());
        contactform.setLinkedinLink(Contact.getLinkedinLink());
        contactform.setFavourate(Contact.isFavourate());
        contactform.setPicture(contactform.getPicture());
        modal.addAttribute("contactForm", contactform);
        modal.addAttribute("contactId", id);
        return "user/View_Contact";
    }


    @PostMapping("/update/{id}")
    public String updateContact(@Valid @ModelAttribute("contactForm") ContactForm contactForm, BindingResult result, @PathVariable("id") String id ){

        if(result.hasErrors()){
            return "user/View_Contact";
        }

        ContactForm contactform=new ContactForm();
        
        var con=new Contact();
        con.setId(id);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavourate(contactForm.isFavourate());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setWebsteLink(contactForm.getWebsteLink());
        con.setLinkedinLink(contactForm.getLinkedinLink());
        // 
        // con.setPicture(contactForm.);
        

        //Processing Image 
        if(contactForm.getPicture()!=null && !contactForm.getPicture().isEmpty()){
        String fileName=UUID.randomUUID().toString();
        String publicImg = imageService.uploadImage(contactForm.getPicture());
        con.setPicture(publicImg);
       
        // contactForm.setC(publicImg);
        }
        // con.setPicture(contactForm.getPicture().toString());

        var updateContact= contactService.upadte(con);
        logger.info("Updated Contact", updateContact);
        return "redirect:/user/contacts";

    }


}


