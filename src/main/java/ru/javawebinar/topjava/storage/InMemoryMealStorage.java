package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealStorage implements MealStorage {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private AtomicInteger idCounter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Breakfast", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Lunch", 1000));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(idCounter.incrementAndGet());
        }
        return storage.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return storage.values();
    }
}
