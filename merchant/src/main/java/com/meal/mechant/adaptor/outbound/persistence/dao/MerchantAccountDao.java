package com.meal.mechant.adaptor.outbound.persistence.dao;


import com.meal.mechant.adaptor.outbound.persistence.po.MerchantAccountPo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantAccountDao extends CrudRepository<MerchantAccountPo, Long> {
    MerchantAccountPo findByMerchantId(long id);
}