package com.rj.appSecurity.domain.userDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequestDto {
    @NotEmpty(message = "First name cannot be empty or null")
    private String firstName;
    @NotEmpty(message = "LastName name cannot be empty or null")
    private String lastName;
    @NotEmpty(message = "Email name cannot be empty or null")
    @Email(message = "Invalid email address")
    private String email;
    @NotEmpty(message = "password name cannot be empty or null")
    @Size(min = 8 , message = "password should more then 8 character")
    private String password;
    private String bio;
    private String phone;
}
