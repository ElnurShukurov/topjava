package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDateTime getCurrentDayStart(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : LocalDateTime.MIN;
    }

    public static LocalDateTime getNextDayStart(LocalDate localDate) {
        return localDate != null ? localDate.plusDays(1).atStartOfDay() : LocalDateTime.MAX;
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T ldt, @Nullable T startTime, @Nullable T endTime) {
        return (startTime == null || ldt.compareTo(startTime) >= 0) && (endTime == null || ldt.compareTo(endTime) < 0);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
