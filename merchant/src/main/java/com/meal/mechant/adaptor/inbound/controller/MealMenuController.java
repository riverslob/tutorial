package com.meal.mechant.adaptor.inbound.controller;


import com.meal.mechant.application.service.MealMenuService;
import com.meal.mechant.domain.MealMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eason
 * @createTime 2019-02-18 下午 11:03
 * @description
 */
@RestController
@RequestMapping(produces = "application/json")
public class MealMenuController {

    @Autowired
    public void setMealMenuService(com.meal.mechant.application.service.MealMenuService mealMenuService) {
        MealMenuService = mealMenuService;
    }

    //    @Autowired
    private MealMenuService MealMenuService;

    @GetMapping("/mealMenu/{mId}")
    public ResponseEntity<MealMenu> get(@PathVariable("mId") Long mId) {
        return ResponseEntity.ok(MealMenuService.findById(mId));
    }


    @PostMapping("/mealMenu/{mId}")
    public ResponseEntity<Void> update(@PathVariable("mId") Long mId, @RequestParam("name") String name, @RequestParam("price") double price) {
        MealMenuService.updateMeal(mId, name, price);
        return ResponseEntity.ok(null);
    }
}
