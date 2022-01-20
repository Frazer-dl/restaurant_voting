package ru.restaurant_voting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {
    private static final LocalTime CONTROL_HOUR = LocalTime.of(11, 0);

    public static boolean isUserVoteInTime() {
        return LocalTime.now().isBefore(CONTROL_HOUR);
    }
}
