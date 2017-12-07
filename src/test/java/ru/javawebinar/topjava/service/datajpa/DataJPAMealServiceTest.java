package ru.javawebinar.topjava.service.datajpa;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_WITH_USER;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles("datajpa")
public class DataJPAMealServiceTest extends MealServiceTest {
	@Test
    public void testGet() throws Exception {
        Meal actual = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        assertThat(actual).isEqualToComparingFieldByField(ADMIN_MEAL_WITH_USER);
    }
}