package com.meal.mechant.adaptor.outbound.persistence.mapper;

import com.meal.mechant.adaptor.outbound.persistence.po.MealMenuPo;
import com.meal.mechant.domain.MealMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MealMenuMapper {
    MealMenuMapper INSTANCE = Mappers.getMapper(MealMenuMapper.class);

    MealMenu poToDomain(MealMenuPo po);

    MealMenuPo domainToPo(MealMenu domain);
}
