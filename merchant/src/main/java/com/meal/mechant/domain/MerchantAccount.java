package com.meal.mechant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantAccount implements Serializable {
    private Long merchantId;
    private String accountNo;
    private BigDecimal amount;

    public void withdraw(BigDecimal withdrawAmount) {
        if(withdrawAmount.compareTo(amount) > 0){
            throw new RuntimeException("withdraw amount large than account mount");
        }
        this.amount = this.amount.subtract(withdrawAmount);
    }
}
