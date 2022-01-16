package ru.restaurant_voting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;

@UtilityClass
public class DateTimeUtil {

    private static final LocalTime CONTROL_HOUR = LocalTime.of(11, 0);

    public static boolean isUserVoteInTime(LocalDateTime localDateTime, LocalDate voteTime) {
        return LocalDate.now().equals(voteTime) && localDateTime.isBefore(ChronoLocalDateTime.from(CONTROL_HOUR));

    }
}
