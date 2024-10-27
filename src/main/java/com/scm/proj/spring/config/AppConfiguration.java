package com.scm.proj.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfiguration {

    @Value("${cloudnary.cloud.name}")
    private String CloudName;

    @Value("${cloudnary.api.key}")
    private String CloudApi;

    @Value("${cloudnary.api.secret}")
    private String CloudSecret;


 
    @Bean
    public Cloudinary cloudinary(){

        return new Cloudinary(
            ObjectUtils.asMap(
                "cloud_name", CloudName,
                "api_key", CloudApi,
                "api_secret", CloudSecret)
            );
    }

}
