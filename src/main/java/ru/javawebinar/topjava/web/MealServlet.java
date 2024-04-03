package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
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
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
        log.debug("Received GET request with action: {}", action);

        int id;
        if (action != null) {
            switch (action) {
                case "delete":
                    id = getIdForRequest(request);
                    log.debug("Deleting meal with id: {}", id);
                    storage.delete(id);
                    log.debug("Meal {} deleted successfully", id);
                    response.sendRedirect("meals");
                    break;
                case "create":
                    log.debug("Preparing to create new meal");
                    Meal newMeal = new Meal(LocalDateTime.now(), "New Meal", 700);
                    log.debug("New meal object created: {}", newMeal);
                    request.setAttribute("formatter", FORMATTER);
                    request.setAttribute("meal", newMeal);
                    request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                    break;
                case "update":
                    id = getIdForRequest(request);
                    log.debug("Preparing to update meal with id: {}", id);
                    Meal existingMeal = storage.get(id);
                    log.debug("Retrieved existing meal for update: {}", existingMeal);
                    request.setAttribute("formatter", FORMATTER);
                    request.setAttribute("meal", existingMeal);
                    request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                    break;
            }
        } else {
            log.debug("No action specified, displaying list of meals");
            request.setAttribute("mealToList", MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            log.debug("Filtered meals retrieved successfully");
            request.setAttribute("formatter", FORMATTER);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        log.debug("Received POST request with meal data: {}", meal);
        storage.save(meal);
        log.debug("Meal saved successfully");
        response.sendRedirect("meals");
    }

    private int getIdForRequest(HttpServletRequest request) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(id);
    }
}
