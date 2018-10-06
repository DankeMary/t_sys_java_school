package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

public class AbstractDaoImpl <T, ID extends Serializable> implements AbstractDao<T, ID> {
    @PersistenceContext
    private EntityManager em = null;

    private final Class<T> clazz;

    public AbstractDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
        getEntityManager();
    }

    public void create(T t) {
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(t);
        getEntityManager().getTransaction().commit();
        getEntityManager().close();
    }

    public void delete(T t) {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(t);
        getEntityManager().getTransaction().commit();
        getEntityManager().close();
    }

    public T find(ID id) {
        return getEntityManager().find(clazz, id);
    }
    //@Transactional
    public void update(T t) {
        //getEntityManager().getTransaction().begin();
        getEntityManager().merge(t);
        //getEntityManager().getTransaction().commit();
        //getEntityManager().close();
    }

    public EntityManager getEntityManager() {
        if (em == null)
            em = EMFactory.getEntityManagerFactory().createEntityManager();
        return em;
    }

    public EntityManager setEntityManager() {
        //????
        return null;
    }
}
