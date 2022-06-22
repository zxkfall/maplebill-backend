package com.flywinter.maplebillbackend.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserInfoDTO {

    @Email
    @NotNull
    private String email;

    @Length(min = 2,max = 255)
    @NotNull
    private String nickname;

    @NotNull
    private String password;

}
