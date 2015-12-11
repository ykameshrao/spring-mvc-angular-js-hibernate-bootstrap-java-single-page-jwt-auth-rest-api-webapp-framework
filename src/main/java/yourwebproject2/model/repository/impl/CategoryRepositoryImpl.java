package yourwebproject2.model.repository.impl;

import yourwebproject2.framework.data.BaseHibernateJPARepository;
import yourwebproject2.model.entity.Category;
import yourwebproject2.model.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Y.Kamesh on 8/2/2015.
 */
@Repository
public class CategoryRepositoryImpl extends BaseHibernateJPARepository<Category, Long> implements CategoryRepository {
    private static Logger LOG = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    @PostConstruct
    public void setUp() {
        LOG.info("categoryRepository created..!");
    }

    @Override
    public Category findByCategoryName(String categoryName) {
        return (Category) sessionFactory.getCurrentSession().createQuery("from Category c where c.name = :categoryName")
                .setParameter("categoryName", categoryName).uniqueResult();
    }

    @Override
    public Category findByCategoryPriority(Integer categoryPriority) {
        return (Category) sessionFactory.getCurrentSession().createQuery("from Category c where c.priority = :categoryPriority")
                .setParameter("categoryPriority", categoryPriority).uniqueResult();
    }

    @Override
    public List<Category> findSubCategories(Category parentCategory) {
        return (List<Category>) sessionFactory.getCurrentSession().createQuery("from Category c where c.parentCategory = :parentCategory")
                .setParameter("parentCategory", parentCategory).list();
    }
}
