package com.meal.mechant.adaptor.outbound.persistence;


import com.meal.mechant.adaptor.outbound.persistence.dao.MerchantAccountDao;
import com.meal.mechant.adaptor.outbound.persistence.mapper.MerchantAccountMapper;
import com.meal.mechant.adaptor.outbound.persistence.po.MerchantAccountPo;
import com.meal.mechant.application.port.MerchantAccountRepository;
import com.meal.mechant.domain.MerchantAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class MerchantAccountRepositoryAdaptor implements MerchantAccountRepository {
    private final MerchantAccountDao merchantAccountDao;

    @Override
    public MerchantAccount findByMerchantId(long id) {
        if (!merchantAccountDao.findAll().iterator().hasNext()) {
            addDemo();
        }
        return MerchantAccountMapper.INSTANCE.poToDomain(merchantAccountDao.findByMerchantId(id));
    }

    @Override
    public void save(MerchantAccount it) {
        merchantAccountDao.save(MerchantAccountMapper.INSTANCE.domainToPo(it));
    }

    private void addDemo() {
        merchantAccountDao.save(new MerchantAccountPo(1L, "Cokee", BigDecimal.valueOf(100)));
        merchantAccountDao.save(new MerchantAccountPo(2L, "Chloe", BigDecimal.valueOf(200)));
        merchantAccountDao.save(new MerchantAccountPo(3L, "Kim", BigDecimal.valueOf(300)));
        merchantAccountDao.save(new MerchantAccountPo(4L, "David", BigDecimal.valueOf(400)));
        merchantAccountDao.save(new MerchantAccountPo(5L, "Michelle", BigDecimal.valueOf(500)));
    }
}
