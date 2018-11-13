package net.tsystems.utilmapper;

import org.mapstruct.Mapper;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Mapper
public class LocalTimeMapper {

    public Time asTime(LocalTime time) {
        return Time.valueOf(time);
    }
    public Timestamp asTimestamp(LocalDate localDate) {
        return Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.ofSecondOfDay(0)));
    }

    public LocalTime asLocalTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime();
    }

    public LocalDateTime asLocalDateTime (LocalTime time) {
        return LocalDateTime.of(LocalDate.now(), time);
    }

    public LocalDateTime asLocalDateTime (LocalDate date) {
        return LocalDateTime.of(date, LocalTime.ofSecondOfDay(0));
    }
}
