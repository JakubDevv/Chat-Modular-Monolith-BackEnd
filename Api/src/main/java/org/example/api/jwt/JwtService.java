package org.example.api.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    public final static String ACCESS_SECRET = "1292104-0FOAipnafniasf-0";

    public final static String REFRESH_SECRET = ";OIH1214oihasihioIOASDHI";

    public String generateAccessToken(String userName, String id){
        return JWT.create()
                .withSubject(userName)
                .withClaim("id", id)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 30 * 1000))
                .sign(Algorithm.HMAC256(ACCESS_SECRET));
    }

    public boolean validateRefreshToken(final String token){
        return JWT.require(Algorithm.HMAC256(REFRESH_SECRET)).build()
                .verify(token).getExpiresAt().after(new Date(System.currentTimeMillis()));
    }

    public String generateRefreshToken(String userName, String id){
        return JWT.create()
                .withSubject(userName)
                .withClaim("id", id)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 30 * 1000))
                .sign(Algorithm.HMAC256(REFRESH_SECRET));
    }

    public String getSubjectRefreshToken(final String token){
        return JWT.require(Algorithm.HMAC256(REFRESH_SECRET)).build()
                .verify(token).getSubject();
    }

    public String getSubjectAccessToken(final String token){
        return JWT.require(Algorithm.HMAC256(ACCESS_SECRET)).build()
                .verify(token).getSubject();
    }

    public String getUserIdFromRefreshToken(final String token){
        return JWT.require(Algorithm.HMAC256(REFRESH_SECRET)).build()
                .verify(token).getClaim("id").asString();
    }

    public String getUserIdByAccessToken(final String token){
        return JWT.require(Algorithm.HMAC256(ACCESS_SECRET)).build()
                .verify(token).getClaim("id").asString();
    }

    public boolean validateAccessToken(final String token, UserDetails userDetails){
        return JWT.require(Algorithm.HMAC256(ACCESS_SECRET)).build()
                .verify(token).getExpiresAt().after(new Date(System.currentTimeMillis())) && (getSubjectAccessToken(token).equals(userDetails.getUsername()));
    }
}
