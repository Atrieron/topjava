package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Р—Р°РІС‚СЂР°Рє", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "РћР±РµРґ", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "РЈР¶РёРЅ", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Р—Р°РІС‚СЂР°Рє", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "РћР±РµРґ", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "РЈР¶РёРЅ", 510)
        );
        List<MealWithExceed> mealsWithExceeded = getFilteredWithExceeded(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExceeded.forEach(System.out::println);
    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal ->
                        new MealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}