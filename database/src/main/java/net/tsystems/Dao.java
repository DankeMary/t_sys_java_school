package net.tsystems;

import org.hibernate.Session;


public interface Dao {
    Session getEntityManager();
}
