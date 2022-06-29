package com.flywinter.maplebillbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/26 16:57
 * Description:
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class Bill extends BaseEntity {

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

    public Bill(BillDTO billDTO) {
        this.email = billDTO.getEmail();
        this.amount = billDTO.getAmount();
        this.description = billDTO.getDescription();
        this.category = billDTO.getCategory();
        this.type = billDTO.getType();
        this.dateTime = billDTO.getDateTime();
    }

    public BillDTO getBillDTO() {
        return new BillDTO(this.email, this.amount, this.description, this.category, this.type, this.dateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bill bill = (Bill) o;
        return category == bill.category && type == bill.type && Objects.equals(email, bill.email) && Objects.equals(amount, bill.amount) && Objects.equals(description, bill.description) && Objects.equals(dateTime, bill.dateTime);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void setBillDTO(BillDTO billDTO) {
        this.email = billDTO.getEmail();
        this.amount = billDTO.getAmount();
        this.description = billDTO.getDescription();
        this.category = billDTO.getCategory();
        this.type = billDTO.getType();
        this.dateTime = billDTO.getDateTime();
    }
}
