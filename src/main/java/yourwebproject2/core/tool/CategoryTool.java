package yourwebproject2.core.tool;

import yourwebproject2.model.entity.Category;
import yourwebproject2.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;

/**
 * @author: kameshr
 */
public class CategoryTool {
    private static Logger LOG = LoggerFactory.getLogger(CategoryTool.class);
    public static void main(String[] args) throws Exception {
        LOG.info("Category Creation Tool");

        if(args==null || args.length < 2) {
            printUsage();
        }

        HashMap<String, String> params = new HashMap<>();
        for(String arg: args) {
            String[] p = arg.split("=");
            if(p == null) {
                continue;
            }

            switch(p[0].toLowerCase()) {
                case "name":
                    params.put("name", p[1]);
                    break;
                case "priority":
                    params.put("priority", p[1]);
                    break;
                case "parent":
                    params.put("parent", p[1]);
                    break;
            }
        }

        Integer priority = null;
        if(params.containsKey("name") && (params.containsKey("priority") || params.containsKey("parent"))) {
            if(params.containsKey("priority")) {
                try {
                    priority = Integer.parseInt(params.get("priority"));
                } catch (NumberFormatException nfe) {
                    System.out.println("Priority not a number");
                    System.exit(1);
                }
            }
        } else {
            printUsage();
        }

        LOG.info("Params: "+params);
        System.out.println("Params: "+params);

        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:/config/spring/appContext-jdbc.xml",
                "classpath:/config/spring/appContext-repository.xml",
                "classpath:/config/spring/appContext-service.xml",
                "classpath:/config/spring/appContext-interceptor.xml"}, true);
        LOG.info("Loaded the context: " + ctx.getBeanDefinitionNames());

        CategoryService categoryService = (CategoryService) ctx.getBean("categoryServiceImpl");

        if(categoryService.isCategoryPresent(params.get("name"))) {
            System.out.println("Category taken");
            System.exit(1);
        }

        Category parentCategory = null;
        if(params.containsKey("parent")) {
            parentCategory = categoryService.findByCategoryName(params.get("parent"));
        }

        if(parentCategory != null && priority != null) {
            if(parentCategory.getPriority() != priority) {
                System.out.println("Sub-Category has to take same priority as parent");
                System.exit(1);
            }
        }

        if(categoryService.isPriorityPresent(priority)) {
            System.out.println("Priority taken");
            System.exit(1);
        }

        Category category = new Category();
        category.setName(params.get("name"));
        category.setPriority(priority);
        if(parentCategory != null) {
            category.setPriority(parentCategory.getPriority());
            category.setParentCategory(parentCategory);
        }

        categoryService.insert(category);

        LOG.info("Category Successfully Created");
    }

    public static void printUsage() {
        System.out.println("java CategoryTool name=<name> priority=<priority> parent=<parent-category>");
        System.exit(1);
    }
}
