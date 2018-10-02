package dao;

import java.io.Serializable;


public interface AbstractDao <T, ID extends Serializable> extends Dao {
        void create(T t);

        public void delete(T t);

        T find(ID id);

        void update(T t);
}
