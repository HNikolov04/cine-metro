package com.cineworld.cinemetro.webapi.controller.user;

import com.cineworld.cinemetro.application.dto.user.CreateUserRequestDto;
import com.cineworld.cinemetro.application.dto.user.UpdateUserRequestDto;
import com.cineworld.cinemetro.application.dto.user.UserDto;
import com.cineworld.cinemetro.application.service.user.UserService;
import com.cineworld.cinemetro.domain.enums.user.UserRole;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) UserRole role) {
        if (role == null) {
            return ResponseEntity.ok(userService.getAllUsers());
        }
        return ResponseEntity.ok(userService.getByRole(role));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email")
    public ResponseEntity<UserDto> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @Valid @RequestBody UpdateUserRequestDto request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }
}
