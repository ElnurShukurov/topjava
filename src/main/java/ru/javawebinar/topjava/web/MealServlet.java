package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<MealTo> mealToList = initializeMealToList();
        request.setAttribute("mealToList", mealToList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    private List<MealTo> initializeMealToList() {
        List<Meal> mealList = new ArrayList<>();
        mealList.add(new Meal(LocalDateTime.of(2024, Month.JANUARY, 30, 10, 0), "Breakfast", 1000));
        mealList.add(new Meal(LocalDateTime.of(2024, Month.JANUARY, 30, 14, 0), "Lunch", 1000));
        mealList.add(new Meal(LocalDateTime.of(2024, Month.JANUARY, 31, 13, 0), "Lunch", 1000));
        mealList.add(new Meal(LocalDateTime.of(2024, Month.JANUARY, 31, 18, 0), "Dinner", 1500));

        return MealsUtil.filteredByStreams(mealList, LocalTime.of(7, 0), LocalTime.of(23, 59), CALORIES_PER_DAY);
    }
}
