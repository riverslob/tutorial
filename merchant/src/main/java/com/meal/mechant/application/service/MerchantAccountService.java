package com.meal.mechant.application.service;

import com.meal.mechant.application.port.MerchantAccountRepository;
import com.meal.mechant.domain.MerchantAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MerchantAccountService {
    private final MerchantAccountRepository merchantAccountRepository;


    public MerchantAccount findByMerchantId(long mId) {
        return merchantAccountRepository.findByMerchantId(mId);
    }

    public void withdraw(Long mId, BigDecimal price) {
        MerchantAccount merchantAccount = findByMerchantId(mId);
        if (Objects.isNull(merchantAccount)) {
            throw new RuntimeException("the Merchant Account not exits!");
        }

        merchantAccount.withdraw(price);
        merchantAccountRepository.save(merchantAccount);
    }
}
