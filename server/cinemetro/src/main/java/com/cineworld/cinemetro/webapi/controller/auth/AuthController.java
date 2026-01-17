package com.cineworld.cinemetro.webapi.controller.auth;

import com.cineworld.cinemetro.application.dto.auth.AuthResponseDto;
import com.cineworld.cinemetro.application.dto.auth.ChangePasswordRequestDto;
import com.cineworld.cinemetro.application.dto.auth.LoginUserRequestDto;
import com.cineworld.cinemetro.application.dto.auth.RegisterUserRequestDto;
import com.cineworld.cinemetro.application.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginUserRequestDto req){
        String token = auth.login(req.email(), req.password());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterUserRequestDto req){
        String token = auth.register(req.email(), req.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDto(token));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequestDto req,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        auth.changePassword(userDetails.getUsername(), req.currentPassword(), req.newPassword());
        return ResponseEntity.noContent().build();
    }
}
