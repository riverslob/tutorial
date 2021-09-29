package com.meal.mechant.application.port;


import com.meal.mechant.domain.MealMenu;

public interface MealMenuRepository {
    MealMenu findById(long id);

    void save(MealMenu it);
}
