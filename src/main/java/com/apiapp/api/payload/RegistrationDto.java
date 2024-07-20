package com.apiapp.api.payload;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegistrationDto {

    private Long id;

@Size(min = 2,message = "should be more than 2 characters")
    private String name;

@Email(message = "invalid email address")
    private String email;


    private String mobile;


    private int pageNo;


}