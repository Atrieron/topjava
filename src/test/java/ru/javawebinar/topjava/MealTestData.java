package ru.javawebinar.topjava;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

import java.time.LocalDateTime;

import ru.javawebinar.topjava.model.Meal;

public class MealTestData {
	public static final int USER_MEAL_ID = START_SEQ + 3;
	public static final Meal USER_MEAL  = new Meal(USER_MEAL_ID, LocalDateTime.of(2015, 5, 30, 13, 0), "Dinner", 250);
	
	public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles");
    }
}
