package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 3;
    public static final int ADMIN_MEAL_ID = START_SEQ + 9;
    public static final int NOT_FOUND = 10;

    public static final Meal user_meal1 = new Meal(MEAL1_ID, of(2024, 5, 3, 12, 0), "User Breakfast", 400);
    public static final Meal user_meal2 = new Meal(MEAL1_ID + 1, of(2024, 5, 3, 18, 0), "User Dinner", 600);
    public static final Meal user_meal3 = new Meal(MEAL1_ID + 2, of(2024, 5, 16, 11, 0), "User Breakfast", 600);
    public static final Meal user_meal4 = new Meal(MEAL1_ID + 3, of(2024, 5, 16, 13, 0), "User Dinner", 650);

    public static final Meal admin_meal1 = new Meal(ADMIN_MEAL_ID, of(2024, 5, 3, 8, 0), "Admin Breakfast", 500);
    public static final Meal admin_meal2 = new Meal(ADMIN_MEAL_ID + 1, of(2024, 5, 3, 14, 0), "Admin Lunch", 700);
    public static final Meal admin_meal3 = new Meal(ADMIN_MEAL_ID + 2, of(2024, 5, 16, 11, 0), "Admin Breakfast", 600);

    public static final List<Meal> meals = Arrays.asList(user_meal4, user_meal3, user_meal2, user_meal1);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2024, 2, 1, 10, 0), "Created meal", 500);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, admin_meal1.getDateTime(), "Updated breakfast", 200);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
