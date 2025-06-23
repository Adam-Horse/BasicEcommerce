package com.adamhorse.basicecommerce.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    public String name;
    public String email;
}
