package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getMealId(request));
        return buildRedirectURL(request);
    }

    @GetMapping("/create")
    public String create(Model model, HttpServletRequest request) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        addFilterParamsToModel(model, request);
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("meal", super.get(getMealId(request)));
        addFilterParamsToModel(model, request);
        return "mealForm";
    }

    @PostMapping
    public String createOrUpdate(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, getMealId(request));
        }
        return buildRedirectURL(request);
    }

    @GetMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        addFilterParamsToModel(model, request);
        return "meals";
    }

    private int getMealId(HttpServletRequest request) {
        String idParam = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(idParam);
    }

    private void addFilterParamsToModel(Model model, HttpServletRequest request) {
        model.addAttribute("startDate", request.getParameter("startDate"));
        model.addAttribute("endDate", request.getParameter("endDate"));
        model.addAttribute("startTime", request.getParameter("startTime"));
        model.addAttribute("endTime", request.getParameter("endTime"));
    }

    private String buildRedirectURL(HttpServletRequest request) {
        StringBuilder redirectURL = new StringBuilder("redirect:/meals");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        if ((startDate != null && !startDate.isEmpty()) ||
                (endDate != null && !endDate.isEmpty()) ||
                (startTime != null && !startTime.isEmpty()) ||
                (endTime != null && !endTime.isEmpty())) {
            redirectURL.append("/filter?");
            if (startDate != null) redirectURL.append("startDate=").append(startDate).append("&");
            if (endDate != null) redirectURL.append("endDate=").append(endDate).append("&");
            if (startTime != null) redirectURL.append("startTime=").append(startTime).append("&");
            if (endTime != null) redirectURL.append("endTime=").append(endTime).append("&");
            redirectURL.deleteCharAt(redirectURL.length() - 1);
        }

        return redirectURL.toString();
    }
}
