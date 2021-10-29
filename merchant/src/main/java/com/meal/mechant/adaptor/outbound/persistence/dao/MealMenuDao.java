package com.meal.mechant.adaptor.outbound.persistence.dao;


import com.meal.mechant.adaptor.outbound.persistence.po.MealMenuPo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealMenuDao extends CrudRepository<MealMenuPo, Long>, JpaSpecificationExecutor, QuerydslPredicateExecutor<MealMenuPo> {
    MealMenuPo findById(long id);
}