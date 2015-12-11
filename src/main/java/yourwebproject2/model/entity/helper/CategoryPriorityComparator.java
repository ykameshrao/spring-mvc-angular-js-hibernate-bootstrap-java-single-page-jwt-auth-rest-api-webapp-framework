package yourwebproject2.model.entity.helper;

import yourwebproject2.model.entity.Job;

import java.util.Comparator;

/**
 * Created by Y.Kamesh on 8/8/2015.
 */
public class CategoryPriorityComparator implements Comparator<Job> {

    /**
     * Compares its two arguments for order. Returns a negative integer, zero, or a positive integer as
     * the first argument is less than, equal to, or greater than the second.
     *
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Job o1, Job o2) {
        // ordering of priority is 1... 2... 3.... N...., where 1 is higher
        if(o1.getCategory().getPriority() > o2.getCategory().getPriority()) {
            return 1;
        } else if(o1.getCategory().getPriority() < o2.getCategory().getPriority()) {
            return -1;
        } else {
            return 0;
        }
    }
}
