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
public class LoginRequestDto {

    @NotEmpty(message = "Email name cannot be empty or null")
    @Email(message = "Invalid email address")
    private String email;
    @NotEmpty(message = "password name cannot be empty or null")
    @Size(message = "password should more then 8 character")
    private String password;
;
}
