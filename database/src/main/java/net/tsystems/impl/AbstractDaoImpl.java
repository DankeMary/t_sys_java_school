package net.tsystems.impl;

import net.tsystems.AbstractDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public abstract class AbstractDaoImpl <T, ID extends Serializable> implements AbstractDao<T, ID> {
    @Autowired
    private SessionFactory sessionFactory;
    private final Class<T> clazz;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @SuppressWarnings("unchecked")
    public AbstractDaoImpl() {
       clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void create(T t) {
        getEntityManager().persist(t);
    }

    public void delete(T t) {
        getEntityManager().delete(t);
    }

    public T find(ID id) {
        return getEntityManager().find(clazz, id);
    }

    public List<T> findAll(){

        return (List<T>)getEntityManager().createQuery( "from " + clazz.getName() ).list();
    }

    public List<T> findAll(int page, int maxResult){
        Query q = getEntityManager().createQuery( "from " + clazz.getName());

        q.setFirstResult((page - 1) * maxResult);
        q.setMaxResults(maxResult);

        return (List<T>) q.list();
    }

    public List<T> findAll(Query q, int page, int maxResult){
        q.setFirstResult((page - 1) * maxResult);
        q.setMaxResults(maxResult);

        return (List<T>) q.list();
    }

    public void update(T t) {
        getEntityManager().merge(t);
        //getEntityManager().update(t);
    }

    public int countPages(int maxResult) {
        Long recordsQty = (Long) getEntityManager()
                .createQuery( "select count(*) from " + clazz.getName()).uniqueResult();
        if (recordsQty != null && maxResult != 0) {
            return (int)Math.ceil((double) recordsQty / maxResult);
        }
        else
            return 0;
    }

    public int countPages(Query q, int maxResult) {
        Long recordsQty = (Long) q.uniqueResult();
        if (recordsQty != null && maxResult != 0) {
            return (int)Math.ceil((double) recordsQty / maxResult);
        }
        else
            return 0;
    }

    public Session getEntityManager() {
        return sessionFactory.getCurrentSession();
    }


}
