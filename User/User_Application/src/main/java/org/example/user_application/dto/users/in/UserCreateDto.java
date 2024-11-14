package org.example.user_application.dto.users.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDto(@NotBlank @Size(max = 25) String userName,
                            @NotBlank @Size(max = 25) String firstName,
                            @NotBlank @Size(max = 25) String lastName,
                            @NotBlank @Size(max = 25) String password,
                            @NotBlank @Size(max = 25) String country,
                            @NotBlank @Size(max = 25) String city) {

}