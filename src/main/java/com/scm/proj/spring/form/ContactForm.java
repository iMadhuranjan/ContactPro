package com.scm.proj.spring.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Phone Number is Required")
    @Pattern(regexp = "^[0-9]{10}", message = "Invalid Phone Number")
    private String phoneNumber;

    @NotBlank(message = "Address is Required")
    private String address;

    
    // @NotEmpty(message = "Please Upload Image Of your Contact")
    private MultipartFile picture;

    private MultipartFile cp;

    private String description;

    private boolean favourate;

    private String WebsteLink;

    private String linkedinLink;

}
