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

    public Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public void create(T t) {
        getSession().persist(t);
    }

    public void delete(T t) {
        getSession().remove(t);
    }

    public T find(ID id) {
        return getSession().find(clazz, id);
    }

    public List<T> findAll(){
        return (List<T>)getSession().createQuery( "from " + clazz.getName() ).list();
    }

    public void update(T t) {
        getSession().merge(t);
    }

    public Session getEntityManager() {
        return getSession();
    }

    public void setEntityManager(Session em) {
        //currentSession = em;
    }
}
