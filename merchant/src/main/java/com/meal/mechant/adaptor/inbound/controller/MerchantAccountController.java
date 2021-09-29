package com.meal.mechant.adaptor.inbound.controller;


import com.meal.mechant.application.service.MerchantAccountService;
import com.meal.mechant.domain.MerchantAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Eason
 * @createTime 2019-02-18 下午 11:03
 * @description
 */
@RestController
@RequestMapping(produces = "application/json")
public class MerchantAccountController {

    @Autowired
    private MerchantAccountService merchantAccountService;


    @GetMapping("/merchant-contracts/{cId}/account")
    public ResponseEntity<MerchantAccount> get(@PathVariable("cId") Long cId,@RequestHeader("mId") Long mId) {
        return ResponseEntity.ok(merchantAccountService.findByMerchantId(mId));
    }


    @PostMapping("/merchant-contracts/{cId}/withdraw/confirm")
    public ResponseEntity<Void> update(@PathVariable("cId") Long cId,@RequestHeader("mId") Long mId, @RequestParam("amount") BigDecimal amount) {
        if(Objects.isNull(amount) || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("withdraw amount is inValid");
        }
        merchantAccountService.withdraw(mId,amount);
        return ResponseEntity.ok(null);
    }
}
