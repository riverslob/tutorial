package com.meal.mechant.adaptor.outbound.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_meal_menu")
public class MealMenuPo extends Persist{
    private String name;
    private double price;

    public MealMenuPo(Long id, String name, double price) {
        super(id);
        this.name = name;
        this.price = price;
    }
}
