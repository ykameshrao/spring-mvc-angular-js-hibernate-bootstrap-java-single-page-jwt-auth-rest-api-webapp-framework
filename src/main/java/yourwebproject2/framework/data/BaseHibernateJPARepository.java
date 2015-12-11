package yourwebproject2.framework.data;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * Generic CRUD Repository class functionality with Hibernate Session Factory
 *
 * Created by Y.Kamesh on 8/2/2015.
 */
public abstract class BaseHibernateJPARepository<T extends Entity, ID extends Serializable> implements BaseJPARepository<T, ID> {
    protected @Autowired
    SessionFactory sessionFactory;
    protected Class<T> clazz;


    @SuppressWarnings("unchecked")
    public void setupEntityClass(Class clazz) {
        this.clazz = clazz;
    }


    public void delete(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }


    @Transactional
    public T insert(T object) {
        sessionFactory.getCurrentSession().setFlushMode(FlushMode.AUTO);
        sessionFactory.getCurrentSession().save(object);
        sessionFactory.getCurrentSession().flush();
        return object;
    }


    @Transactional
    public T update(T object) {
        sessionFactory.getCurrentSession().setFlushMode(FlushMode.AUTO);
        sessionFactory.getCurrentSession().update(object);
        sessionFactory.getCurrentSession().flush();
        return object;
    }


    @Transactional
    public T insertOrUpdate(T object) {
        sessionFactory.getCurrentSession().setFlushMode(FlushMode.AUTO);
        sessionFactory.getCurrentSession().saveOrUpdate(object);
        sessionFactory.getCurrentSession().flush();
        return object;
    }


    @Transactional(readOnly = true)
    public T findById(ID id) {
        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }


    public Collection<T> findAllByPage(int pageNum, int countPerPage, Order order) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(clazz);
        c.setMaxResults(countPerPage);
        c.setFirstResult(pageNum * countPerPage);
        return c.list();
    }
}
