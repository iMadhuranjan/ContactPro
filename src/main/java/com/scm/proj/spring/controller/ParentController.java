package com.scm.proj.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.proj.spring.entity.User;
import com.scm.proj.spring.helper.Helper;
import com.scm.proj.spring.services.userService;


@ControllerAdvice
public class ParentController {

    @Autowired
    private userService service;
    
     @ModelAttribute
        public void LoggedInUserData(Authentication authentication, Model model){

            if(authentication==null){
                return;
            }
        String user = Helper.getEmailOfLoggedInUser(authentication);
        User user1=service.getUserByEmail(user);

        System.out.println("User Name is " + user1.getName());
        System.err.println("User Email is "+ user1.getEmail());
        model.addAttribute("loggedInUser", user1);
    }
    
}