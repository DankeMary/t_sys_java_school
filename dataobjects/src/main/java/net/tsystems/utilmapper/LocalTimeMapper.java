package net.tsystems.utilmapper;

import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

@Mapper
public class LocalTimeMapper {
    public Timestamp asTimestamp(LocalTime localTime) {
        Timestamp timeArr = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date parsedDate = dateFormat.parse(localTime.toString());
            timeArr = new java.sql.Timestamp(parsedDate.getTime());
        }catch (ParseException e) {
            //TODO what to do?
        }
        return timeArr;
    }

    public LocalTime asLocalTime(Timestamp sqlTimestamp) {
        return sqlTimestamp != null ? sqlTimestamp.toLocalDateTime().toLocalTime() : null;
    }
}
