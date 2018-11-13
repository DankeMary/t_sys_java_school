package net.tsystems.utilmapper;

import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Mapper
public class LocalTimeMapper {
    public Timestamp asTimestamp(LocalTime localTime) {


            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            //Date parsedDate = dateFormat.parse(localTime.toString());
//            timeArr = new java.sql.Timestamp(parsedDate.getTime());
        //->Date / LocalDateTime
        //*LocalDateTime localDateTime = new LocalDateTime(LocalDate.now(), localTime);
        //of
        //*return Timestamp.valueOf(parsedDate);
        return null;
    }

    /*public LocalTime asLocalTime(Timestamp sqlTimestamp) {
        return sqlTimestamp != null ? sqlTimestamp.toLocalDateTime().toLocalTime() : null;
    }*/

    public LocalTime asLocalTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime();
    }

    public LocalDateTime asLocalDateTime (LocalTime time) {
        return LocalDateTime.of(LocalDate.now(), time);
    }
}
