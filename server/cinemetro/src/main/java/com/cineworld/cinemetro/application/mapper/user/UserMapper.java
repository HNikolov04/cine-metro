package com.cineworld.cinemetro.application.mapper.user;

import com.cineworld.cinemetro.application.dto.user.CreateUserRequestDto;
import com.cineworld.cinemetro.application.dto.user.UpdateUserRequestDto;
import com.cineworld.cinemetro.application.dto.user.UserDto;
import com.cineworld.cinemetro.domain.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user){
        if(user == null) return null;

        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }

    public User toEntity(UserDto userDto){
        if(userDto == null) return null;

        return User.builder()
                .id(userDto.id())
                .email(userDto.email())
                .role(userDto.role())
                .build();
    }

    public User fromCreateRequest(CreateUserRequestDto request){
        if(request == null) return null;

        return User.builder()
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .build();
    }

    public void applyUpdate(User user, UpdateUserRequestDto request){
        user.setEmail(request.email());
        user.setRole(request.role());
    }
}
