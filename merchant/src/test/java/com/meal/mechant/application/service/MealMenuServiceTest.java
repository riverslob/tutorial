package com.meal.mechant.application.service;

import com.meal.mechant.application.port.MealMenuRepository;
import com.meal.mechant.domain.MealMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MealMenuServiceTest extends TestBase {
    @Mock
    private MealMenuRepository mealMenuRepository;
    @InjectMocks
    private MealMenuService mealMenuService;
    private MealMenuServiceUser mealMenuServiceUser;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
        mealMenuServiceUser = new MealMenuServiceUser(mealMenuService);
    }

    @Test
    @DisplayName("Given Calculator, When Adding 2 number, Then should add two number")
    public void should_update_meal_success_when_data_is_correct() {
        //given
        MealMenu mealMenu = MealMenu.builder().id(1L).name("mealMenu").price(10).build();
        when(mealMenuRepository.findById(1L)).thenReturn(mealMenu);

        //when
        mealMenuServiceUser.updateMeal(1L, "name2", 20);

        //then
        assertEquals("name2", mealMenu.getName());
        assertEquals(20, mealMenu.getPrice(), 0.00001);

//        verify(mealMenuRepository).findById(2L);
        verify(mealMenuRepository).findById(eq(1L));
    }
}
