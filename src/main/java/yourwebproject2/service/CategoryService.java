package yourwebproject2.service;

import yourwebproject2.framework.data.BaseService;
import yourwebproject2.framework.exception.NotFoundException;
import yourwebproject2.model.entity.Category;

import java.util.List;

/**
 * Brings in the basic CRUD service ops from BaseService. Insert additional ops here.
 *
 * Created by Y.Kamesh on 8/2/2015.
 */
public interface CategoryService extends BaseService<Category, Long> {
    /**
     * Validates whether the given category already
     * exists in the system.
     *
     * @param categoryName
     *
     * @return
     */
    public boolean isCategoryPresent(String categoryName);

    /**
     * Validates whether the given category priority already
     * exists in the system.
     *
     * @param priorityId
     *
     * @return
     */
    public boolean isPriorityPresent(Integer priorityId);

    /**
     * Find category by name
     *
     * @param categoryName
     * @return
     */
    public Category findByCategoryName(String categoryName) throws NotFoundException;

    /**
     * Find sub categories by parent category
     *
     * @param parentCategory
     * @return
     */
    public List<Category> findSubCategories(Category parentCategory) throws NotFoundException;
}
