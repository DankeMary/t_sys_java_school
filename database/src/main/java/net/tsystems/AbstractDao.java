package net.tsystems;

import java.io.Serializable;
import java.util.List;


public interface AbstractDao <T, ID extends Serializable> extends Dao {
    void create(T t);

    void delete(T t);

    T find(ID id);

    List<T> findAll();

    void update(T t);
}
