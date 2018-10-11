package net.tsystems.impl;

import net.tsystems.AbstractDao;
import net.tsystems.EMFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

public class AbstractDaoImpl <T, ID extends Serializable> implements AbstractDao<T, ID> {
    @PersistenceContext
    private EntityManager em = null;

    //@Autowired
    private SessionFactory sessionFactory;

    private final Class<T> clazz;

    public AbstractDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
        getEntityManager();
    }

    public void create(T t) {
        getEntityManager().persist(t);
    }

    public void delete(T t) {
        getEntityManager().remove(t);
    }

    public T find(ID id) {
        return getEntityManager().find(clazz, id);
    }
    //@Transactional
    public void update(T t) {
        getEntityManager().merge(t);
    }

    public EntityManager getEntityManager() {
        if (em == null)
            em = EMFactory.getEntityManagerFactory().createEntityManager();
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
