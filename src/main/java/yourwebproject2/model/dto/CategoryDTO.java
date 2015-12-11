package yourwebproject2.model.dto;

/**
 *
 * {"name":"cat1", "priority":2, "parent":"pCat"}
 *
 * @author: kameshr
 */
public class CategoryDTO {
    String name;
    Integer priority;
    String parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                ", parent='" + parent + '\'' +
                '}';
    }
}
