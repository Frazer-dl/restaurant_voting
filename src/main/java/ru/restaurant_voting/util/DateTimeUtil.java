package ru.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateTimeUtil {

    // DB doesn't support LocalDate.MIN/MAX
    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1, 0, 0);
    private static final Integer controlHour = 11;

    public static LocalDateTime atStartOfDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime atStartOfNextDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE;
    }

    public static boolean isUserVoteInTime(LocalDateTime localDateTime) {
        return isBetweenHalfOpen(localDateTime, getStartDate(localDateTime), getEndDate(localDateTime));
    }

    public static LocalDateTime getStartDate(LocalDateTime localDateTime) {
        return localDateTime.getHour() < controlHour ? localDateTime.withHour(0).withMinute(0).minusSeconds(0).withNano(0)
                : localDateTime.withHour(controlHour).withMinute(0).minusSeconds(0).withNano(0);
    }

    public static LocalDateTime getEndDate(LocalDateTime localDateTime) {
        return localDateTime.getHour() < controlHour ? localDateTime.withHour(controlHour).withMinute(0).minusSeconds(0).withNano(0)
                : localDateTime.plusDays(1L).withHour(controlHour).withMinute(0).minusSeconds(0).withNano(0);
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }
}
