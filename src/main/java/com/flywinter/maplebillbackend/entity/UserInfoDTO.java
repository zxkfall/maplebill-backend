package com.flywinter.maplebillbackend.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfoDTO that = (UserInfoDTO) o;
        return Objects.equals(email, that.email) && Objects.equals(nickname, that.nickname) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nickname, password);
    }
}
