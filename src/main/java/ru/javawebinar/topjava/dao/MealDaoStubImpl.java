package ru.javawebinar.topjava.dao;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import ru.javawebinar.topjava.model.Meal;

public class MealDaoStubImpl implements MealDao {
	private static int maxId = 0;
	private static final MealDaoStubImpl instance = new MealDaoStubImpl();

	private MealDaoStubImpl() {

	}

	public static MealDaoStubImpl getInstance() {
		return instance;
	}

	private List<Meal> meals = new ArrayList<>();
	{
		addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 500));
		addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Dinner", 1000));
		addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Supper", 500));
		addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Breakfast", 1000));
		addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Dinner", 500));
		addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Supper", 510));
	}

	@Override
	public List<Meal> getMeals() {
		return meals;
	}

	@Override
	public Meal getById(int id) {
		synchronized (meals) {
			for (Meal meal : meals)
				if (meal.getId() == id)
					return meal;
		}
		return null;
	}

	@Override
	public void removeById(int id) {
		int j = -1;
		synchronized (meals) {
			for (int i = 0; i < meals.size(); ++i)
				if (meals.get(i).getId() == id) {
					j = i;
					break;
				}
			if (j > -1)
				meals.remove(j);
		}
	}

	@Override
	public synchronized int getNextId() {
		return ++maxId;
	}

	@Override
	public void addMeal(Meal meal) {
		synchronized (meals) {
			meal.setId(getNextId());
			meals.add(meal);
		}
	}
}
