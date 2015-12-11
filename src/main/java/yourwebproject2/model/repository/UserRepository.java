package yourwebproject2.model.repository;

import yourwebproject2.framework.data.BaseJPARepository;
import yourwebproject2.model.entity.User;

/**
 *
 * DD Repository for User related actions and events
 *
 * @author: kameshr
 */
public interface UserRepository extends BaseJPARepository<User, Long> {
    /**
     * Finds a user with the given email
     *
     * @param email
     * @return
     */
    public User findByEmail(String email);
}
