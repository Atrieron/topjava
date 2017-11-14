package ru.javawebinar.topjava.dao;

import java.util.Collection;
import java.util.List;

import ru.javawebinar.topjava.model.Meal;

public interface MealDao {
	Collection<Meal> getMeals();
	Meal getById(int id);
	void removeById(int id);
	int getNextId();
	void save(Meal meal);
}