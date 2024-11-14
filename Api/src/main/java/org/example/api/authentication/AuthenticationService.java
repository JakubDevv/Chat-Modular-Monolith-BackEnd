package org.example.api.authentication;

import models.User;
import org.example.api.dto.RefreshToken;
import org.example.api.dto.Tokens;
import org.example.api.dto.UserAuthRequest;
import org.example.api.dto.UserCreatedDto;
import org.example.api.jwt.JwtService;
import org.example.user_application.dto.users.in.UserCreateDto;
import org.example.shared.s3.S3Service;
import org.example.user_application.services.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final S3Service s3Service;

    public AuthenticationService(JwtService jwtService, AuthenticationManager authenticationManager, IUserService userService, S3Service s3Service) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.s3Service = s3Service;
    }

    @Override
    public Tokens authenticate(UserAuthRequest userAuthRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthRequest.username(), userAuthRequest.password()));
        if (authenticate.isAuthenticated()) {
            User userByUsername = userService.getUserByUsername(userAuthRequest.username());
            return new Tokens(jwtService.generateRefreshToken(
                    userAuthRequest.username(),
                    userByUsername.getId().value().toString()
            ),
                    jwtService.generateAccessToken(userAuthRequest.username(),
                            userByUsername.getId().value().toString()));
        } else {
            throw new RuntimeException("Wrong username or password");
        }    }

    @Override
    public Tokens getAccessToken(RefreshToken refreshToken) {
        if(jwtService.validateRefreshToken(refreshToken.value())){
            return new Tokens(jwtService.generateRefreshToken(
                    jwtService.getSubjectRefreshToken(refreshToken.value()),
                    jwtService.getUserIdFromRefreshToken(refreshToken.value())),
                    jwtService.generateAccessToken
                            (
                                    jwtService.getSubjectRefreshToken(refreshToken.value()),
                                    jwtService.getUserIdFromRefreshToken(refreshToken.value())
                            )
            );
        }
        else {
            throw new IllegalArgumentException("Wrong refresh token");
        }
    }

    @Override
    public UserCreatedDto saveUser(UserCreateDto userCreateDto) {
        return null;
//        return userService.saveUser(new User(userCreateDto.userName(), userCreateDto.firstName(), userCreateDto.lastName(), userCreateDto.password(), userCreateDto.country(), userCreateDto.city()));
    }

    @Override
    public void setUserPhoto(Long userId, InputStream file) {
           try {
               s3Service.putObject("Chat/User" + userId, file.readAllBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }


}
