package net.tsystems.serviceobject;

public class TrainSO {
    private int id;
    private int number;
    private int capacity;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainSO trainSO = (TrainSO) o;

        if (id != trainSO.id) return false;
        if (number != trainSO.number) return false;
        return capacity == trainSO.capacity;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number;
        result = 31 * result + capacity;
        result = 31 * result;
        return result;
    }
}
