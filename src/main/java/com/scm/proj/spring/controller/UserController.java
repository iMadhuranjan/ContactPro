package com.scm.proj.spring.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.proj.spring.entity.User;
import com.scm.proj.spring.form.UsersForm;
import com.scm.proj.spring.helper.Helper;
import com.scm.proj.spring.services.userService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private userService service;

        private  Logger logger = LoggerFactory.getLogger(UserController.class);
    // User Dashboard 
    @GetMapping("/dashboard")
    public String userDashboard(Authentication authentication, org.springframework.ui.Model modal){
        String name = Helper.getEmailOfLoggedInUser(authentication);
         User user= service.getUserByEmail(name);
        modal.addAttribute("user", user);
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userProfile( Authentication authentication, org.springframework.ui.Model modal){
        String name = Helper.getEmailOfLoggedInUser(authentication);
         User user= service.getUserByEmail(name);
         System.out.println(user.getName());
        System.out.println(user.getUsername());
        modal.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/profile/update")
    public String updateProfile( Authentication authentication, org.springframework.ui.Model modal){
        String name = Helper.getEmailOfLoggedInUser(authentication);
        User user1= service.getUserByEmail(name);
        logger.info("User Email n", name);
        modal.addAttribute("user", user1);
        return "user/UpdateProfile";
    }

    @PostMapping("/profile/update/{id}")
    public String postMethodName (@PathVariable String id, @ModelAttribute("usersForm") UsersForm usersForm) {
        // String name = Helper.getEmailOfLoggedInUser(authentication);
        
        UsersForm userform=new UsersForm();


        var nu= new User();
        nu.setUserId(id);
        logger.info("usern Nme", nu.getUsername());
        nu.setName(userform.getName());
        nu.setAbout(userform.getAbout());
        nu.setEmail(userform.getEmail());
        nu.setPhoneNumber(usersForm.getPhoneNumber());
        

        service.updaUser(nu);
        return "redirect:user/profile";
    }
    



}
