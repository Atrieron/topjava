package ru.javawebinar.topjava.dao;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.javawebinar.topjava.model.Meal;

public class MealDaoStubImpl implements MealDao {
	private static int maxId = 0;
	private static final MealDaoStubImpl instance = new MealDaoStubImpl();

	private MealDaoStubImpl() {

	}

	public static MealDaoStubImpl getInstance() {
		return instance;
	}

	private Map<Integer, Meal> meals = new HashMap<>();
	{
		save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 500));
		save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Dinner", 1000));
		save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Supper", 500));
		save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Breakfast", 1000));
		save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Dinner", 500));
		save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Supper", 510));
	}

	@Override
	public Collection<Meal> getMeals() {
		return meals.values();
	}

	@Override
	public Meal getById(int id) {
		synchronized (meals) {
			if(meals.containsKey(id)) {
				return meals.get(id);
			}
		}
		return null;
	}

	@Override
	public void removeById(int id) {
		int j = -1;
		synchronized (meals) {
			if(meals.containsKey(id)) {
				meals.remove(id);
			}
		}
	}

	@Override
	public synchronized int getNextId() {
		return ++maxId;
	}

	@Override
	public void save(Meal meal) {
		if(meal.getId()==0) {
			meal.setId(getNextId());
		}
		synchronized (meals) {			
			meals.put(meal.getId(), meal);
		}
	}
}
