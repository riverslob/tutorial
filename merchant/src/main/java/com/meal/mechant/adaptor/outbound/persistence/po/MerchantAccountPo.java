package com.meal.mechant.adaptor.outbound.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity(name = "t_merchant_account")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantAccountPo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long merchantId;
    private String accountNo;
    private BigDecimal amount;


}
