package net.tsystems.utilmapper;

import org.mapstruct.Mapper;

import java.sql.Date;
import java.time.LocalDate;

@Mapper
public class LocalDateMapper {
    public Date asSqlDate(LocalDate localDate) {
        return localDate != null ? Date.valueOf(localDate) : null;
    }

    public LocalDate asLocalDate(Date sqlDate) {
        return sqlDate != null ? sqlDate.toLocalDate() : null;
    }
}
