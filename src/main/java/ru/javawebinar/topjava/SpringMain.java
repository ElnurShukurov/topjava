package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            List<MealTo> filteredMeals = mealRestController.getFiltered(
                    LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(9, 0),
                    LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(13, 0));
            filteredMeals.forEach(System.out::println);
            System.out.println("---------------------------");
            System.out.println(mealRestController.getAll());
            System.out.println("---------------------------");
            System.out.println(mealRestController.get(1));
            System.out.println("---------------------------");
            System.out.println(mealRestController.getFiltered(null, null, null, null));
        }
    }
}
