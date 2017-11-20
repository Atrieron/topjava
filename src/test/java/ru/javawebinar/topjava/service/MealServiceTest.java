package ru.javawebinar.topjava.service;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.USER_MEAL;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"
})

@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
	@Autowired
	private MealService mealService;
	
	@Test
    public void get() throws NotFoundException{
		Meal meal = mealService.get(USER_MEAL_ID, USER_ID);
		assertMatch(meal, USER_MEAL);
    }

    public void delete(int id, int userId) throws NotFoundException{
    }

    public List<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId){
    	return null;
    }
    
    public List<Meal> getAll(int userId){
    	return null;
    }

    @Test
    public void update() throws NotFoundException{
    	Meal updated = new Meal(USER_MEAL);
        updated.setDescription("UpdatedName");
        updated.setCalories(330);
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(USER_MEAL_ID, USER_ID), updated);
    }

    public Meal create(Meal meal, int userId) {
    	return null;
    }
}