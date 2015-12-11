package yourwebproject2.controller;

import yourwebproject2.framework.api.APIResponse;
import yourwebproject2.framework.controller.BaseController;
import yourwebproject2.model.dto.CategoryDTO;
import yourwebproject2.model.entity.Category;
import yourwebproject2.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Category creation and get APIs
 *
 * Created by Y.Kamesh on 8/2/2015.
 */
@Controller
@RequestMapping("category")
public class CategoryController extends BaseController {
    private static Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    /**
     * Method to handle creation of the category by extracting the categoryInfo json from
     * POST body expected in the format - {"name":"cat1", "priority":2, "parent":"pCat"}
     *
     * @param categoryDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = {JSON_API_CONTENT_HEADER})
    public @ResponseBody
    APIResponse createCategory(@RequestBody CategoryDTO categoryDTO) throws Exception {
        if(StringUtils.isEmpty(categoryDTO.getName())
                && (categoryDTO.getPriority()==null || StringUtils.isEmpty(categoryDTO.getParent()))) {
            return APIResponse.toErrorResponse("required params missing. format - {\"name\":\"cat1\", \"priority\":2, \"parent\":\"pCat\"}");
        }

        if(categoryService.isCategoryPresent(categoryDTO.getName())) {
            LOG.info("Category taken: "+categoryDTO.getName());
            return APIResponse.toErrorResponse("Category taken");
        }

        Category parentCategory = null;
        if(!StringUtils.isEmpty(categoryDTO.getParent())) {
            parentCategory = categoryService.findByCategoryName(categoryDTO.getParent());
        }

        if(parentCategory != null && categoryDTO.getPriority() != null) {
            if(parentCategory.getPriority() != categoryDTO.getPriority()) {
                return APIResponse.toErrorResponse("Sub-Category has to take same priority as parent");
            }
        }

        if(categoryService.isPriorityPresent(categoryDTO.getPriority())) {
            LOG.info("Category Priority taken: "+categoryDTO.getPriority());
            return APIResponse.toErrorResponse("Priority taken");
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setPriority(categoryDTO.getPriority());
        if(parentCategory != null) {
            category.setPriority(parentCategory.getPriority());
            category.setParentCategory(parentCategory);
        }

        categoryService.insert(category);
        return APIResponse.toOkResponse(category);
    }

    /**
     * Method to get the category by given id
     * GET
     *
     * @param catId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById/{catId}", method = RequestMethod.GET)
    public @ResponseBody
    APIResponse getCategoryById(@PathVariable Long catId) throws Exception {
        Category category = categoryService.findById(catId);
        return APIResponse.toOkResponse(category);
    }

    /**
     * Method to get the category by given name
     * GET
     *
     * @param catName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByName/{catName}", method = RequestMethod.GET)
    public @ResponseBody
    APIResponse getCategoryByName(@PathVariable String catName) throws Exception {
        Category category = categoryService.findByCategoryName(catName);
        return APIResponse.toOkResponse(category);
    }


    /**
     * Method to get the sub categories for a parent category by given name
     * GET
     *
     * @param parentCatName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSubCategoriesByName/{parentCatName}", method = RequestMethod.GET)
    public @ResponseBody
    APIResponse getSubCategoriesByName(@PathVariable String parentCatName) throws Exception {
        Category category = categoryService.findByCategoryName(parentCatName);

        List<Category> subCategories = null;
        if(category != null) {
            subCategories = categoryService.findSubCategories(category);
        }

        return APIResponse.toOkResponse(subCategories);
    }


    /**
     * Method to get the sub categories for a parent category by given id
     * GET
     *
     * @param parentCatId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSubCategoriesById/{parentCatId}", method = RequestMethod.GET)
    public @ResponseBody
    APIResponse getSubCategoriesById(@PathVariable Long parentCatId) throws Exception {
        Category category = categoryService.findById(parentCatId);

        List<Category> subCategories = null;
        if(category != null) {
            subCategories = categoryService.findSubCategories(category);
        }

        return APIResponse.toOkResponse(subCategories);
    }
}