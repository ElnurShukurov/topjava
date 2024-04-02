package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private MealStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new InMemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("mealToList", MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            request.setAttribute("formatter", formatter);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getIdForRequest(request);
            storage.delete(id);
            response.sendRedirect("meals");
        } else if (action.equals("create")) {
            Meal newMeal = new Meal(LocalDateTime.now(), "New Meal", 700);
            request.setAttribute("meal", newMeal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        } else if (action.equals("update")) {
            Meal newMeal = storage.get(getIdForRequest(request));
            request.setAttribute("meal", newMeal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
        log.debug("redirect to meals");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        storage.save(meal);
        response.sendRedirect("meals");
    }

//    private static List<MealTo> initializeMealToList() {
//        List<Meal> mealList = Arrays.asList(
//                new Meal(LocalDateTime.of(2024, Month.JANUARY, 30, 10, 0), "Breakfast", 1000),
//                new Meal(LocalDateTime.of(2024, Month.JANUARY, 30, 14, 0), "Lunch", 1000),
//                new Meal(LocalDateTime.of(2024, Month.JANUARY, 31, 13, 0), "Lunch", 1000),
//                new Meal(LocalDateTime.of(2024, Month.JANUARY, 31, 18, 0), "Dinner", 1500)
//        );
//        return MealsUtil.filteredByStreams(mealList, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
//    }

    private int getIdForRequest(HttpServletRequest request) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(id);
    }
}
