package ru.javawebinar.topjava.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.USER_MEAL_BREAKFAST30;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_DINNER30;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_SUPPER30;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_BREAKFAST31;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_SUPPER31;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_LATESUPPER31;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;

import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ContextConfiguration({ "classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml" })

@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
	@Autowired
	private MealService mealService;

	@Test
	public void get() throws NotFoundException {
		Meal meal = mealService.get(USER_MEAL_ID, USER_ID);
		assertMatch(meal, USER_MEAL_DINNER30);
	}

	@Test(expected = NotFoundException.class)
	public void getNotFound() throws Exception {
		mealService.get(1, USER_ID);
	}

	@Test(expected = NotFoundException.class)
	public void getWrongUser() throws Exception {
		mealService.get(USER_MEAL_ID, ADMIN_ID);
	}

	@Test
	public void delete() throws NotFoundException {
		mealService.delete(USER_MEAL_ID, USER_ID);
		assertMatch(mealService.getAll(USER_ID), USER_MEAL_LATESUPPER31, USER_MEAL_SUPPER31, USER_MEAL_BREAKFAST31,
				USER_MEAL_SUPPER30, USER_MEAL_BREAKFAST30);
	}

	@Test(expected = NotFoundException.class)
	public void deleteWrongMeal() throws NotFoundException {
		mealService.delete(USER_MEAL_ID, ADMIN_ID);
	}

	// @Test
	public void getBetweenDateTimes() {
		List<Meal> res = mealService.getBetweenDates(LocalDate.of(2015, 5, 30), LocalDate.of(2015, 5, 31),
				AuthorizedUser.id());
		assertMatch(res, USER_MEAL_SUPPER31, USER_MEAL_DINNER30);
	}

	@Test
	public void getAll() {
		List<Meal> all = mealService.getAll(USER_ID);
		assertMatch(all, USER_MEAL_LATESUPPER31, USER_MEAL_SUPPER31, USER_MEAL_BREAKFAST31, USER_MEAL_SUPPER30,
				USER_MEAL_DINNER30, USER_MEAL_BREAKFAST30);
	}

	@Test
	public void update() throws NotFoundException {
		Meal updated = new Meal(USER_MEAL_DINNER30);
		updated.setDescription("UpdatedName");
		updated.setCalories(330);
		mealService.update(updated, USER_ID);
		assertMatch(mealService.get(USER_MEAL_ID, USER_ID), updated);
	}
	
	@Test(expected = NotFoundException.class)
	public void updateWrongUser() throws NotFoundException {
		Meal updated = new Meal(USER_MEAL_DINNER30);
		updated.setDescription("UpdatedName");
		updated.setCalories(330);
		mealService.update(updated, ADMIN_ID);
	}

	@Test
	public void create() {
		Meal meal = new Meal(LocalDateTime.of(2015, 5, 31, 12, 0), "dinner", 110);
		Meal created = mealService.create(meal, USER_ID);
		meal.setId(created.getId());
		assertMatch(mealService.getAll(USER_ID), USER_MEAL_LATESUPPER31, USER_MEAL_SUPPER31, meal,
				USER_MEAL_BREAKFAST31, USER_MEAL_SUPPER30, USER_MEAL_DINNER30, USER_MEAL_BREAKFAST30);
	}
	
	@Test(expected = DuplicateKeyException.class)
	public void createAtSameDate() {
		Meal meal = new Meal(LocalDateTime.of(2015, 5, 31, 13, 0), "dinner", 110);
		mealService.create(meal, USER_ID);
	}
}