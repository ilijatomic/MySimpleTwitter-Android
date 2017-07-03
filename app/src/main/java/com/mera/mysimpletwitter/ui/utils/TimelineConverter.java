package com.mera.mysimpletwitter.ui.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by ilija.tomic on 7/3/2017.
 */
public class TimelineConverter {

    private static final String DATE_TIME_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";

    public static String dateToAge(String createdAt, DateTime now) {
        if (createdAt == null) {
            return "";
        }

        DateTimeFormatter dtf = DateTimeFormat.forPattern(DATE_TIME_FORMAT);
        try {
            DateTime created = dtf.parseDateTime(createdAt);

            if (Seconds.secondsBetween(created, now).getSeconds() < 60) {
                return Seconds.secondsBetween(created, now).getSeconds() + "s";
            } else if (Minutes.minutesBetween(created, now).getMinutes() < 60) {
                return Minutes.minutesBetween(created, now).getMinutes() + "m";
            } else if (Hours.hoursBetween(created, now).getHours() < 24) {
                return Hours.hoursBetween(created, now).getHours() + "h";
            } else {
                return Days.daysBetween(created, now).getDays() + "d";
            }
        } catch (IllegalArgumentException e) {
            return "";
        }
    }
}
