package com.meal.mechant.adaptor.outbound.persistence;


import com.meal.mechant.adaptor.outbound.persistence.dao.MealMenuDao;
import com.meal.mechant.adaptor.outbound.persistence.mapper.MealMenuMapper;
import com.meal.mechant.adaptor.outbound.persistence.po.MealMenuPo;
import com.meal.mechant.application.port.MealMenuRepository;
import com.meal.mechant.domain.MealMenu;
import org.springframework.stereotype.Component;

@Component
public class MealMenuRepositoryAdaptor implements MealMenuRepository {
    private final MealMenuDao mealMenuDao;

    public MealMenuRepositoryAdaptor(MealMenuDao mealMenuDao) {
        this.mealMenuDao = mealMenuDao;
    }

    @Override
    public MealMenu findById(long id) {
        if (!mealMenuDao.findAll().iterator().hasNext()) {
            addDemo();
        }
        return MealMenuMapper.INSTANCE.poToDomain(mealMenuDao.findById(id));
    }

    @Override
    public void save(MealMenu it) {
        mealMenuDao.save(MealMenuMapper.INSTANCE.domainToPo(it));
    }

    private void addDemo() {
        mealMenuDao.save(new MealMenuPo(1L, "Cokee", 1.0));
        mealMenuDao.save(new MealMenuPo(2L, "Chloe", 2.0));
        mealMenuDao.save(new MealMenuPo(3L, "Kim", 3.0));
        mealMenuDao.save(new MealMenuPo(4L, "David", 4.0));
        mealMenuDao.save(new MealMenuPo(5L, "Michelle", 5.0));
    }
}