package org.example.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.example.api.authentication.AuthenticationService;
import org.example.api.dto.RefreshToken;
import org.example.api.dto.Tokens;
import org.example.api.dto.UserAuthRequest;
import org.example.api.dto.UserCreatedDto;
import org.example.user_application.dto.users.in.UserCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserCreatedDto> createNewUser(@Valid @RequestBody UserCreateDto userCreateDTO) {
        return new ResponseEntity<>(authenticationService.saveUser(userCreateDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> authenticate(@Valid @RequestBody UserAuthRequest userAuthRequest) {
        return new ResponseEntity<>(authenticationService.authenticate(userAuthRequest), HttpStatus.OK);
    }

    @PostMapping("/access-token")
    public ResponseEntity<Tokens> getNewAccessToken(@Valid @RequestBody RefreshToken refreshToken){
        return new ResponseEntity<>(authenticationService.getAccessToken(refreshToken), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/photo")
    public ResponseEntity<Void> setUserPhoto(@Valid @Min(1) @RequestParam Long userId, @RequestParam(value = "file") MultipartFile file){
        try (InputStream byteFile = file.getInputStream()){
            authenticationService.setUserPhoto(userId, byteFile);
        } catch (IOException e) {

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
