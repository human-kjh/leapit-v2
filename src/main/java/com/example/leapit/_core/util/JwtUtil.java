package com.example.leapit._core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.leapit.user.User;

import java.util.Date;

public class JwtUtil {
    public static String createRefresh(User user) {
        String jwt = JWT.create()
                .withSubject("blog")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 일주일
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("name", user.getName())
                .sign(Algorithm.HMAC512("metacoding"));
        return jwt;
    }

    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject("blog")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("name", user.getName())
                .sign(Algorithm.HMAC512("metacoding"));
        return jwt;
    }

    public static User verify(String jwt) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("metacoding")).build().verify(jwt);
        int id = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getClaim("username").asString();
        String name = decodedJWT.getClaim("name").asString();

        return User.builder()
                .id(id)
                .username(username)
                .name(name)
                .build();
    }
}