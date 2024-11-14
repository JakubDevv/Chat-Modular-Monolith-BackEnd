package org.example.api.authentication;

import org.example.api.dto.RefreshToken;
import org.example.api.dto.Tokens;
import org.example.api.dto.UserAuthRequest;
import org.example.api.dto.UserCreatedDto;
import org.example.user_application.dto.users.in.UserCreateDto;

import java.io.IOException;
import java.io.InputStream;

public interface IAuthenticationService {

    Tokens authenticate(UserAuthRequest userAuthRequest);

    Tokens getAccessToken(RefreshToken refreshToken);

    UserCreatedDto saveUser(UserCreateDto userCreateDto);

    void setUserPhoto(Long userId, InputStream file) throws IOException;
}
