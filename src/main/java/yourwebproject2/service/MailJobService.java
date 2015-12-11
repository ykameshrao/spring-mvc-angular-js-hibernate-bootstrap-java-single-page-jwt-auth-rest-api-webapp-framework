package yourwebproject2.service;

import yourwebproject2.framework.data.BaseService;
import yourwebproject2.model.entity.Job;
import yourwebproject2.model.entity.User;

/**
 * @author: kameshr
 */
public interface MailJobService extends BaseService<Job, Long> {

    /**
     * Sends the confirmation mail to user.
     *
     * @param user
     */
    public void sendConfirmationMail(User user);
}
