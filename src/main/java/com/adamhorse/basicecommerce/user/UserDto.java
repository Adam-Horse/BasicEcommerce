package com.adamhorse.basicecommerce.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private final Long id;
    private final String name;
    private final String email;

}
