package net.tsystems.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "train")
public class TrainDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "number", nullable = false)
    private int number;
    @Column(name = "capacity", nullable = false)
    private int capacity;
    @Column(name = "price", nullable = false)
    private BigDecimal price;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainDO trainDO = (TrainDO) o;

        if (id != trainDO.id) return false;
        if (number != trainDO.number) return false;
        return (capacity != trainDO.capacity);
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
