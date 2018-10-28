package net.tsystems.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "station")
@SuppressWarnings("JpaAttributeTypeInspection")
public class StationDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", nullable = false, length = 65)
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationDO stationDO = (StationDO) o;
        return id == stationDO.id &&
                Objects.equals(name, stationDO.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
