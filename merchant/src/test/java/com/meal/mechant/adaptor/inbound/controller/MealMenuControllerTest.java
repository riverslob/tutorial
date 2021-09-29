package com.meal.mechant.adaptor.inbound.controller;

import com.meal.mechant.adaptor.outbound.persistence.dao.MealMenuDao;
import com.meal.mechant.adaptor.outbound.persistence.po.MealMenuPo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MealMenuControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private MealMenuDao mealMenuDao;

    @Test
    public void should_return_result_when_get_meal() throws Exception {
//        given :
        when(mealMenuDao.findById(1L)).thenReturn(MealMenuPo.builder().id(1L).name("name1").price(10).build());

//        when:
        ResultActions response = mvc.perform(get("/mealMenu/1"));

//        then:
        response.andExpect(jsonPath("$.price").value(10));
    }

    @Test
    public void should_success_when_update_meal() throws Exception {
//        given :
        when(mealMenuDao.findById(1L)).thenReturn(MealMenuPo.builder().id(1L).name("name1").price(10).build());
        when(mealMenuDao.findAll()).thenReturn(new ArrayList<>());

//        when:
        ResultActions response = mvc.perform(post("/mealMenu/1?name={name}&price={price}"));

//        then:
        response.andExpect(status().isOk());
    }
}