package dataobject;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "station")
@SuppressWarnings("JpaAttributeTypeInspection")
public class StationDO {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", nullable = false, length = 65)
    private String name;
    @OneToMany(mappedBy = "from"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private Collection<TripDO> fromTrips;
    @OneToMany(mappedBy = "to"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private Collection<TripDO> toTrips;

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

    public Collection<TripDO> getFromTrips() {
        return fromTrips;
    }
    public void setFromTrips(Collection<TripDO> fromTrips) {
        this.fromTrips = fromTrips;
    }

    public Collection<TripDO> getToTrips() {
        return toTrips;
    }
    public void setToTrips(Collection<TripDO> toTrips) {
        this.toTrips = toTrips;
    }
    //TODO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationDO stationDO = (StationDO) o;

        if (id != stationDO.id) return false;
        if (name != null ? !name.equals(stationDO.name) : stationDO.name != null) return false;

        return true;
    }
    //TODO
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
