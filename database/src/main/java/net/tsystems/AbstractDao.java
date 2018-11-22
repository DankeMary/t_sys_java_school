package net.tsystems;

import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;


public interface AbstractDao <T, ID extends Serializable> extends Dao {
    void create(T t);

    void delete(T t);

    T find(ID id);

    List<T> findAll();

    List<T> findAll(int page, int maxResult);

    void update(T t);

    int countPages(int maxResult);

    int countPages(Query q, int maxResult);
}
