package com.flywinter.maplebillbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserInfo extends BaseEntity {

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @Length(min = 2, max = 255)
    @NotNull
    private String nickname;

    @NotNull
    private String password;

    private String roles;

    public UserInfo(UserInfoDTO userInfoDTO) {
        this.email = userInfoDTO.getEmail();
        this.nickname = userInfoDTO.getNickname();
        this.password = userInfoDTO.getPassword();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(email, userInfo.email) && Objects.equals(nickname, userInfo.nickname) && Objects.equals(password, userInfo.password) && Objects.equals(roles, userInfo.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, nickname, password, roles);
    }
}
