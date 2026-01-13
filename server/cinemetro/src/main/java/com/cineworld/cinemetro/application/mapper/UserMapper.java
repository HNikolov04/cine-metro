package com.cineworld.cinemetro.application.mapper;

import com.cineworld.cinemetro.application.dto.RegisterUserRequestDto;
import com.cineworld.cinemetro.application.dto.UserDto;
import com.cineworld.cinemetro.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user){
        if(user == null) return null;

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public User toEntity(UserDto userDto){
        if(userDto == null) return null;

        return User.builder()
                .id(userDto.id())
                .email(userDto.email())
                .role(userDto.role())
                .build();
    }
    public User registerRequestToUser(RegisterUserRequestDto request){
        if(request == null) return null;

        return User.builder()
                .email(request.email())
                .password(request.password())
                .build();
    }
}
