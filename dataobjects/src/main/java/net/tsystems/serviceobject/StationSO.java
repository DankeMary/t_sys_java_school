package net.tsystems.serviceobject;

public class StationSO {
    private int id;
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

        StationSO stationSO = (StationSO) o;

        if (id != stationSO.id) return false;
        if (!name.equals(stationSO.name)) return false;
        return name.equals(stationSO.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result;
        result = 31 * result;
        return result;
    }
}
