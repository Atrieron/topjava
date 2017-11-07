package ru.javawebinar.topjava.dao;

import java.util.List;

import ru.javawebinar.topjava.model.Meal;

public interface MealDao {
	List<Meal> getMeals();
	Meal getById(int id);
	void removeById(int id);
	int getNextId();
	void addMeal(Meal meal);
}