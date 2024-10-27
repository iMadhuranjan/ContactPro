package com.scm.proj.spring.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.proj.spring.entity.User;
import com.scm.proj.spring.entity.provider;
import com.scm.proj.spring.helper.AppConstant;
import com.scm.proj.spring.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.var;

@Component
public class OAuthAuthenticationHandler implements AuthenticationSuccessHandler  {

    Logger logger= LoggerFactory.getLogger(OAuthAuthenticationHandler.class);


    @Autowired
    private UserRepo repo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

               var OAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;


            // 



                String OAuth2AuthenticationTokenProvider = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId().toString();

                logger.info(OAuth2AuthenticationTokenProvider);

                DefaultOAuth2User oauthuser= (DefaultOAuth2User) authentication.getPrincipal();

                if(OAuth2AuthenticationTokenProvider.equalsIgnoreCase("google")){
                    DefaultOAuth2User user= (DefaultOAuth2User) authentication.getPrincipal();

                    // Logging 
                    logger.info(user.getName());
                    
                    user.getAttributes().forEach((key, value)->{
                        logger.info("{} => {}", key, value);
                    });
        
                    logger.info(user.getAuthorities().toString());
        
                    // Fetch User Info 
                    String name=user.getAttribute("name");
                    String email=user.getAttribute("email");
                    String picture=user.getAttribute("picture");
        
                    // Save User in user1 Object 
                    User user1=new User();
        
                    user1.setName(name);
                    user1.setEmail(email);
                    user1.setProfilePic(picture);
                    user1.setPassword("password");
                    user1.setUserId(UUID.randomUUID().toString());
                    user1.setProvider(provider.GOOGLE);
                    user1.setEnabled(true);
                    user1.setEmailVerified(true);
                    user1.setRoleList(List.of(AppConstant.ROLE_USER));
                    user1.setAbout("This Account is Created Using Google");
                    user1.setProviderUserId(user.getName());
                   // Check Wether is Avaibble for Not
        
        
                  User user2=  repo.findByEmail(email).orElse(null);
        
                    if(user2==null){
                        repo.save(user1);
                        logger.info("User Saved = " + email);
                    }
                    new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
                }


                if(OAuth2AuthenticationTokenProvider.equalsIgnoreCase("github")){
                    DefaultOAuth2User user= (DefaultOAuth2User) authentication.getPrincipal();
                    // Fetch User Info 
                    String name=user.getAttribute("name")!=null? user.getAttribute("name"): user.getAttribute("login");
                    String email=user.getAttribute("email")!=null ? user.getAttribute("email"): user.getAttribute("login").toString()+"@gmail.com" ;
                    String picture=user.getAttribute("avatar_url");
        
                    // Save User in user1 Object 
                    User user1=new User();
        
                    user1.setName(name);
                    user1.setEmail(email);
                    user1.setProfilePic(picture);
                    user1.setPassword("password");
                    user1.setUserId(UUID.randomUUID().toString());
                    user1.setProvider(provider.GITHUB);
                    user1.setEnabled(true);
                    user1.setEmailVerified(true);
                    user1.setRoleList(List.of(AppConstant.ROLE_USER));
                    user1.setAbout("This Account is Created Using Github");
                    user1.setProviderUserId(user.getName());
                  
                  User user2=  repo.findByEmail(email).orElse(null);
        
                    if(user2==null){
                        repo.save(user1);
                        logger.info("User Saved = " + email);
                    }
                    new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
                }

                // oauthuser.getAttribute("name");

                // oauthuser.getAttributes().forEach((key, value)->{
                //     logger.info("{} = {}", key, value);
                // });
                
                // User user=new User();
                // user.setUserId(UUID.randomUUID().toString());
                // user.setEnabled(true);
                // user.setEmailVerified(true);
                // user.setRoleList(List.of(AppConstant.ROLE_USER));
                // user.setPassword("jnvckhcivyb78cgxdcbc46");


                // if(OAuth2AuthenticationTokenProvider.equalsIgnoreCase("google")){

                //     user.setName(oauthuser.getAttribute("name").toString());
                //     user.setEmail(oauthuser.getAttribute("email").toString());
                //     user.setProfilePic(oauthuser.getAttribute("picture").toString());
                //     user.setProviderUserId(oauthuser.getName().toString());
                //     user.setAbout("Your are Register Through Google");
                //     user.setProvider(provider.GOOGLE);
                // }else if(OAuth2AuthenticationTokenProvider.equalsIgnoreCase("github")){

                //     String email = oauthuser.getAttribute("email")!=null ? oauthuser.getAttribute("email").toString() : oauthuser.getAttribute("login").toString()+"gmail.com";
                //    String picture=oauthuser.getAttribute("avatar_url").toString();
                //    String name=oauthuser.getAttribute("login").toString();
                //    String providerUserId= oauthuser.getName();

                //    user.setName(name);
                //    user.setProfilePic(picture);
                //    user.setEmail(email);
                //    user.setProviderUserId(providerUserId);
                //    user.setAbout("You are Register through Github");
                //    user.setProvider(provider.GITHUB);

                // }


                // User user2=  repo.findByEmail(user.getEmail()).orElse(null);

                //     if(user2==null){
                //         repo.save(user2);
                //         logger.info("User Saved = " + user2);
                //     }

                //     new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");



















           

    }

}
