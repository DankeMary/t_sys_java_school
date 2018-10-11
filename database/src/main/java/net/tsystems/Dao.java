package net.tsystems;

import javax.persistence.EntityManager;

public interface Dao {
    EntityManager getEntityManager();
    void setEntityManager(EntityManager em);
}
