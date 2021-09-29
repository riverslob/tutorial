package com.meal.mechant.application.service;

import com.meal.mechant.application.port.MealMenuRepository;
import com.meal.mechant.domain.MealMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MealMenuService {
    private final MealMenuRepository mealMenuRepository;

    public MealMenu findById(Long mId) {
        return mealMenuRepository.findById(mId);
    }

    public void updateMeal(Long mId, String name, double price) {
        Optional.ofNullable(findById(mId)).ifPresent(it -> {
            it.update(name, price);
            mealMenuRepository.save(it);
        });
    }
}
