package com.meal.mechant.application.port;


import com.meal.mechant.domain.MerchantAccount;

public interface MerchantAccountRepository {
    MerchantAccount findByMerchantId(long id);

    void save(MerchantAccount it);
}
