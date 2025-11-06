package com.cineworld.cinemetro.application.mapper;


import com.cineworld.cinemetro.application.dto.RegisterUserRequestDto;
import com.cineworld.cinemetro.application.dto.UserDto;
import com.cineworld.cinemetro.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User registerRequestToUser(RegisterUserRequestDto request);
}