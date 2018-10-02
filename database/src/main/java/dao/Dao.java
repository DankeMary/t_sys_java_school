package dao;

import javax.persistence.EntityManager;

public interface Dao {
    EntityManager getEntityManager();
    EntityManager setEntityManager();
}
