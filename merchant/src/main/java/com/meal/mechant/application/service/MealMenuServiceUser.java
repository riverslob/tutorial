package com.meal.mechant.application.service;

import com.meal.mechant.domain.MealMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MealMenuServiceUser {
    private final MealMenuService mealMenuService;

    public MealMenu findById(long mId) {
        return mealMenuService.findById(mId);
    }

    public void updateMeal(Long mId, String name, double price) {
        mealMenuService.updateMeal(mId, name, price);
    }
}
