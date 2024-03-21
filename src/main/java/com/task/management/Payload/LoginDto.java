package com.task.management.Payload;

import lombok.Data;

@Data
public class LoginDto {

	private String usernameOrEmail;
    private String password;

}
