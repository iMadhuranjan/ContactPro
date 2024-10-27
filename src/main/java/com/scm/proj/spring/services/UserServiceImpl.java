package com.scm.proj.spring.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.proj.spring.helper.*;
import com.scm.proj.spring.entity.User;
import com.scm.proj.spring.repositories.UserRepo;

@Service
public class UserServiceImpl implements userService{

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public User savUser(User user) {
        String UserId= UUID.randomUUID().toString();

        user.setUserId(UserId);

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstant.ROLE_USER));
        return repo.save(user);
    }

    @Override
    public User updaUser(User user) {
      User user2=  repo.findById(user.getUserId()).orElseThrow(()-> new ResourcesNotFoundException());
    //   user2.setUserId(user.getUserId());
    user2.setName(user.getUsername());
    user2.setAbout(user.getAbout());
    // user2.setEmail(user.getEmail());
    user2.setPhoneNumber(user.getPhoneNumber());
    // user2.setProfilePic(user.getProfilePic());
    // user2.setPassword(user.getPassword());
    // user2.setEmailVerified(user.isEmailVerified());
    // user2.setNumberVerified(user.isNumberVerified());
    // user2.setProvider(user.getProvider());
    // user2.setProviderUserId(user.getProviderUserId());
    return   repo.save(user2);
   
    }

    @Override
    public Optional<User> getUserById(String id) {
        return repo.findById(id);
    }

    @Override
    public void deleteUser(String id) {
       User user=  repo.findById(id).orElseThrow(()-> new ResourcesNotFoundException("User ID Not Found"));
       repo.delete(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> users=repo.findAll();
        return  users;
    }

    @Override
    public boolean isUserExist(String id) {
        User user=repo.findById(id).orElse(null);
        return user!=null? true :false;
       
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = repo.findByEmail(email).orElse(null);
        return user!=null? true :false; 
       }

    @Override
    public User getUserByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }

}
