package com.meal.mechant.adaptor.outbound.persistence.mapper;

import com.meal.mechant.adaptor.outbound.persistence.po.MerchantAccountPo;
import com.meal.mechant.domain.MerchantAccount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MerchantAccountMapper {
    MerchantAccountMapper INSTANCE = Mappers.getMapper(MerchantAccountMapper.class);

    MerchantAccount poToDomain(MerchantAccountPo po);

    MerchantAccountPo domainToPo(MerchantAccount domain);
}
