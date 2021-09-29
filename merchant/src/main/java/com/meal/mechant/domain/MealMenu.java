package com.meal.mechant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealMenu implements Serializable {
    private Long id;
    private String name;
    private double price;


    public void update(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
