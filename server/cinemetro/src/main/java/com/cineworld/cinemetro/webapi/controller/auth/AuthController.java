package com.cineworld.cinemetro.webapi.controller.auth;

import com.cineworld.cinemetro.application.dto.auth.LoginUserRequestDto;
import com.cineworld.cinemetro.application.dto.user.RegisterUserRequestDto;
import com.cineworld.cinemetro.application.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService auth;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginUserRequestDto req){
        return auth.login(req.email(), req.password());
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterUserRequestDto req){
        auth.register(req.email(), req.password());
        return "User created";
    }
}
