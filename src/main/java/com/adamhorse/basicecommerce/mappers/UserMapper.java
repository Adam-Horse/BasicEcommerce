package com.adamhorse.basicecommerce.mappers;

import com.adamhorse.basicecommerce.dtos.RegisterUserRequest;
import com.adamhorse.basicecommerce.dtos.UpdateUserRequest;
import com.adamhorse.basicecommerce.dtos.UserDto;
import com.adamhorse.basicecommerce.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
