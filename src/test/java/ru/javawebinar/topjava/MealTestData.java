package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 8;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, of(2015, Month.MAY, 30, 10, 0), "Р—Р°РІС‚СЂР°Рє", 500);
    public static final Meal MEAL2 = new Meal(MEAL1_ID + 1, of(2015, Month.MAY, 30, 13, 0), "РћР±РµРґ", 1000);
    public static final Meal MEAL3 = new Meal(MEAL1_ID + 2, of(2015, Month.MAY, 30, 20, 0), "РЈР¶РёРЅ", 500);
    public static final Meal MEAL4 = new Meal(MEAL1_ID + 3, of(2015, Month.MAY, 31, 10, 0), "Р—Р°РІС‚СЂР°Рє", 500);
    public static final Meal MEAL5 = new Meal(MEAL1_ID + 4, of(2015, Month.MAY, 31, 13, 0), "РћР±РµРґ", 1000);
    public static final Meal MEAL6 = new Meal(MEAL1_ID + 5, of(2015, Month.MAY, 31, 20, 0), "РЈР¶РёРЅ", 510);
    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL_ID, of(2015, Month.JUNE, 1, 14, 0), "РђРґРјРёРЅ Р»Р°РЅС‡", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL_ID + 1, of(2015, Month.JUNE, 1, 21, 0), "РђРґРјРёРЅ СѓР¶РёРЅ", 1500);
    
    static
    {
    	MEAL1.setUser(USER);
    	MEAL2.setUser(USER);
    	MEAL3.setUser(USER);
    	MEAL4.setUser(USER);
    	MEAL5.setUser(USER);
    	MEAL6.setUser(USER);
    	
    	ADMIN_MEAL1.setUser(ADMIN);
    	ADMIN_MEAL2.setUser(ADMIN);
    }

    public static final List<Meal> MEALS = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

    public static Meal getCreated() {
        Meal ret = new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "РЎРѕР·РґР°РЅРЅС‹Р№ СѓР¶РёРЅ", 300);
        ret.setUser(USER);
		return ret;
    }

    public static Meal getUpdated() {
        Meal ret = new Meal(MEAL1_ID, MEAL1.getDateTime(), "РћР±РЅРѕРІР»РµРЅРЅС‹Р№ Р·Р°РІС‚СЂР°Рє", 200);
        ret.setUser(MEAL1.getUser());
		return ret;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}