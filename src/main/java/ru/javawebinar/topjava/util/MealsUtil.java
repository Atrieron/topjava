package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Р—Р°РІС‚СЂР°Рє", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "РћР±РµРґ", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "РЈР¶РёРЅ", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Р—Р°РІС‚СЂР°Рє", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "РћР±РµРґ", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "РЈР¶РёРЅ", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<MealWithExceed>  getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        return null;
    }
}
