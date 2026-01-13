package com.cineworld.cinemetro.application.auth;

import com.cineworld.cinemetro.application.dto.LoginUserRequestDto;
import com.cineworld.cinemetro.application.dto.RegisterUserRequestDto;
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
    public String login(@RequestBody LoginUserRequestDto req){
        return auth.login(req.email(), req.password());
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterUserRequestDto req){
        auth.register(req.email(), req.password());
        return "User created";
    }
}
