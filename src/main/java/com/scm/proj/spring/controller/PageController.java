package com.scm.proj.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.proj.spring.entity.User;
import com.scm.proj.spring.form.UsersForm;
import com.scm.proj.spring.services.userService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class PageController {

@Autowired
private userService service;

    @GetMapping("/")
    public String getMethodName() {
        return "redirect:/home";
    }
    

    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("Madhu", "Madhuranjan Kushwaha");
        model.addAttribute("youtube", " Online Kamai");
        model.addAttribute("linkedin","https://linkedin.com/iMadhuranjan");
        return "home";
    }


    // @GetMapping("/logout")
    // public String logout() {
    //     return "logout";
    // }
    
    
    // @GetMapping("/about")
    // public String about() {
    //     return "about";
    // }
    

    // @GetMapping("/service")
    // public String service(Model model) {
    //     model.addAttribute("isactive", false);
    //     return "service";
    // }
    
    // @GetMapping("/contact")
    // public String contact(){
    //     return "contact";
    // }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        UsersForm userForm= new UsersForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

//    Register Process 

// @PostMapping("/process")
// public String postMethodName(@Valid @ModelAttribute("userForm") UsersForm form, BindingResult entity, HttpSession session) {
//    if(entity.hasErrors()){
//     return "register";
//    }else{
//     User user= new User();
//     user.setName(form.getName());
//     user.setAbout(form.getAbout());
//     user.setEmail(form.getEmail());
//     user.setPassword(form.getPassword());
//     user.setPhoneNumber(form.getPhoneNumber());
//     user.setProfilePic("https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg");

//     service.savUser(user);
//     session.setAttribute("message", "You Have Been Registered Successfully");
//     return "redirect:/register";
//    }
// }

@PostMapping("/process")
public String RegisterProcess(@Valid @ModelAttribute("userForm") UsersForm myForm, BindingResult result, HttpSession session) {

// Validation
if(result.hasErrors()){
    return "register";
}

//Saving User  
    
    User user= new User();
    user.setName(myForm.getName());
    user.setAbout(myForm.getAbout());
    user.setEmail(myForm.getEmail());
    user.setPassword(myForm.getPassword());
    user.setPhoneNumber(myForm.getPhoneNumber());
    user.setProfilePic("https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg");

    User savedUser = service.savUser(user);
    session.setAttribute("message", "You Have Been Registered Successfully");

   System.out.println(savedUser);
    return "redirect:/register";
}

    
}
