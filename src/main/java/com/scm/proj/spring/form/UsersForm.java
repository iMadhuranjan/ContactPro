package com.scm.proj.spring.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersForm {

    @NotBlank(message = "Username is Required")
    @Size(min = 3, message = "Minimum 3 Characters are Required")
    private String name;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6, message = "Minimum 6 Characters are Required")
    private String password;

    @NotBlank(message = "About is Required")
    private String about;

    @Size(min = 8, max = 12, message = "Invalid Phone Number")
    private String phoneNumber;
}
