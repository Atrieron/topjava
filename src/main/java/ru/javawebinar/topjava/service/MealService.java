package ru.javawebinar.topjava.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

public interface MealService {
	Meal create(Meal meal);

    void delete(int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    void update(Meal meal);

    List<Meal> getAll();
    
    List<Meal> getInPeriod(LocalDate periodStard, LocalDate periodEnd, LocalTime timeStart, LocalTime timeEnd);
}