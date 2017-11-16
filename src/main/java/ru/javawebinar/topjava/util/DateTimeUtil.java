package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	// public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime
	// endTime) {
	// return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
	// }

	// public static boolean isBetween(LocalDate ld, LocalDate startDate, LocalDate
	// endDate) {
	// return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
	// }

	public static <T extends Comparable<? super T>> boolean isBetween(T ld, T startDate, T endDate) {
		return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
	}

	public static String toString(LocalDateTime ldt) {
		return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
	}

	public static LocalDate parseLocalDateOrDefault(String exp, LocalDate defaultValue) {
		if (exp != null && !exp.isEmpty()) {
			try {
				return LocalDate.parse(exp);
			} catch (DateTimeParseException ex) {
			}
		}
		return defaultValue;
	}
	
	public static LocalTime parseLocalTimeOrDefault(String exp, LocalTime defaultValue) {
		if (exp != null && !exp.isEmpty()) {
			try {
				return LocalTime.parse(exp);
			} catch (DateTimeParseException ex) {
			}
		}
		return defaultValue;
	}
}
