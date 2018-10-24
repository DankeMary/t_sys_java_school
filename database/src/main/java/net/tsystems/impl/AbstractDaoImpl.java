package net.tsystems.impl;

import net.tsystems.AbstractDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        getEntityManager().remove(t);
    }

    public T find(ID id) {
        return getEntityManager().find(clazz, id);
    }

    public List<T> findAll(){
        return (List<T>)getEntityManager().createQuery( "from " + clazz.getName() ).list();
    }

    public void update(T t) {
        getEntityManager().merge(t);
    }

    public Session getEntityManager() {
        return sessionFactory.getCurrentSession();
    }
}
