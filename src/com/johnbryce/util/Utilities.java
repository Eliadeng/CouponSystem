package com.johnbryce.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class Utilities {
	java.util.Date utDate = new java.util.Date();
	java.sql.Date sqlDate = new java.sql.Date(utDate.getDate());

	public static java.sql.Date convertDataToSql(java.util.Date date) {
		return new Date(date.getYear(), date.getMonth(), date.getDay());
	}

	public static java.util.Date realDate(int year, int month, int day) {
		return new java.util.Date(year - 1900, month - 1, day - 1);
	}

	public static java.util.Date calcDate(int year, int month, int dayOfMonth) {
		ZoneId defaultZoneID = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
		java.util.Date date = java.util.Date.from(localDate.atStartOfDay(defaultZoneID).toInstant());
		return date;
	}
}
