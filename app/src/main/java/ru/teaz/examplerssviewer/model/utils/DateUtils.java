package ru.teaz.examplerssviewer.model.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by Sergey on 31.05.2016.
 */
public final class DateUtils {

    private static final String PUB_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z"; //"Sat, 21 Nov 2015 16:56:04 +0300"
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormat.forPattern(PUB_DATE_FORMAT).withLocale(Locale.ENGLISH);

    private DateUtils() {
    }

    @Nullable
    public static LocalDateTime parseStringToDateTime(@NonNull final String date) {
        LocalDateTime dateTime = null;

        try {
            dateTime = FORMATTER.parseLocalDateTime(date);
        } catch (IllegalArgumentException e) {
            //TODO Handle exception
        }
        return dateTime;
    }
}
