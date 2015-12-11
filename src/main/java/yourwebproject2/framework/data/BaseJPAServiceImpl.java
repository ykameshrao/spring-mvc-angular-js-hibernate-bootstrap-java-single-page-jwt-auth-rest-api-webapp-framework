package yourwebproject2.framework.data;

import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.Collection;

/**
 * BaseService implementation for basic access to service
 * methods of CRUD operations on entity
 *
 * @author : Y Kamesh Rao
 */
public abstract class BaseJPAServiceImpl<T extends Entity, ID extends Serializable> implements BaseService<T, ID> {
    protected BaseJPARepository<T, ID> baseJpaRepository;
    protected Class<T> entityClass;

    public T insert(T object) throws Exception {
        return baseJpaRepository.insert(object);
    }

    public T update(T object) throws Exception {
        return baseJpaRepository.update(object);
    }

    public void delete(T object) throws Exception {
        baseJpaRepository.delete(object);
    }

    public T findById(ID id) throws Exception {
        T result = baseJpaRepository.findById(id);

        if (result != null)
            return result;
        else
            throw new Exception("Not Found");
    }

    public Collection<T> findAllByPage(int pageNum, int countPerPage, Order order) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
