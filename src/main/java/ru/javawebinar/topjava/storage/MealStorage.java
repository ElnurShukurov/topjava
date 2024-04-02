package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealStorage {
    Meal save(Meal meal);

    void delete(int id);

    Meal get(int id);

    Collection<Meal> getAll();
}
