package com.scm.proj.spring.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.proj.spring.services.SecurityCustomUserDetailService;


@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService customUserDetailService;
    
@Autowired
private OAuthAuthenticationHandler authAuthenticationHandler;

public DaoAuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(customUserDetailService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
}



@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable);
   
    http.authorizeHttpRequests(authorize -> {
        // authorize.requestMatchers("/home", "/register", "/services").permitAll();
        authorize.requestMatchers("/user/**").authenticated();
        authorize.anyRequest().permitAll();
    });

        http.formLogin(formlogin -> {
            formlogin.loginPage("/login");
            formlogin.loginProcessingUrl("/authenticate");
            // formlogin.successForwardUrl("/user/profile");
            // formlogin.failureForwardUrl("/login?rerror=true");
            formlogin.usernameParameter("email");
            formlogin.passwordParameter("password");
        });

      

        http.logout(logout->{
            logout.logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout=true");
        });


    http.oauth2Login(auth->{
        auth.loginPage("/login");
        auth.successHandler(authAuthenticationHandler);
    });

    return http.build();
}

@Bean
public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
}

}
