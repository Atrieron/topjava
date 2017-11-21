package ru.javawebinar.topjava;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

import java.time.LocalDateTime;
import java.util.Arrays;

import ru.javawebinar.topjava.model.Meal;

public class MealTestData {
	public static final int USER_MEAL_ID = START_SEQ + 3;
	public static final Meal USER_MEAL_BREAKFAST30  = new Meal(START_SEQ + 2, LocalDateTime.of(2015, 5, 30, 10, 0), "Breakfast", 150);
	public static final Meal USER_MEAL_DINNER30  = new Meal(USER_MEAL_ID, LocalDateTime.of(2015, 5, 30, 13, 0), "Dinner", 250);
	public static final Meal USER_MEAL_SUPPER30  = new Meal(START_SEQ + 4, LocalDateTime.of(2015, 5, 30, 20, 0), "Supper", 225);
	public static final Meal USER_MEAL_BREAKFAST31  = new Meal(START_SEQ + 5, LocalDateTime.of(2015, 5, 30, 10, 0), "Breakfast", 175);
	public static final Meal USER_MEAL_SUPPER31  = new Meal(START_SEQ + 6, LocalDateTime.of(2015, 5, 30, 13, 0), "Supper", 225);
	public static final Meal USER_MEAL_LATESUPPER31  = new Meal(START_SEQ + 7, LocalDateTime.of(2015, 5, 30, 20, 0), "Late Supper", 500);
	
	public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles");
    }
	
	public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
