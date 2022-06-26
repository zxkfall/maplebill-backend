package com.flywinter.maplebillbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/26 19:49
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    @NotBlank
    private String email;
    @NotNull
    private BigDecimal amount;

    private String description;
    @NotNull
    private int category;
    @NotNull
    private int type;

    @NotNull
    private LocalDateTime dateTime;

}
