package yourwebproject2.model.repository;

import yourwebproject2.framework.data.BaseJPARepository;
import yourwebproject2.model.entity.Category;

import java.util.List;

/**
 * CRUD operations come from Base Repo but additional operations can be defined here.
 *
 * @author: kameshr
 */
public interface CategoryRepository extends BaseJPARepository<Category, Long> {
    /**
     * Finds a category with the given categoryName
     *
     * @param categoryName
     * @return
     */
    public Category findByCategoryName(String categoryName);

    /**
     * Finds a category with the given categoryPriority
     *
     * @param categoryPriority
     * @return
     */
    public Category findByCategoryPriority(Integer categoryPriority);

    /**
     * Finds sub categories with the given parentCategory
     *
     * @param parentCategory
     * @return
     */
    public List<Category> findSubCategories(Category parentCategory);
}
