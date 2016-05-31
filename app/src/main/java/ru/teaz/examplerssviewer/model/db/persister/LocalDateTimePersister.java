package ru.teaz.examplerssviewer.model.db.persister;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.SQLException;

/**
 * Created by Sergey on 31.05.2016.
 */
public class LocalDateTimePersister extends BaseDataType{

    private static final LocalDateTimePersister INSTANCE = new LocalDateTimePersister();

    private static final String DATE_TIME_FORMAT     = "yyyy-MM-dd HH:mm:ss";
    private DateTimeFormatter formatter;

    private LocalDateTimePersister() {
        super(SqlType.STRING, new Class[]{LocalDateTime.class});
        formatter = DateTimeFormat.forPattern(DATE_TIME_FORMAT);
    }

    public static LocalDateTimePersister getSingleton() {
        return INSTANCE;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) throws SQLException {
        return null;
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        if (sqlArg == null) {
            return null;
        }

        final LocalDateTime dateTime = formatter.parseLocalDateTime((String) sqlArg);
        return dateTime;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) throws SQLException {
        return formatter.print((LocalDateTime) javaObject);
    }
}
