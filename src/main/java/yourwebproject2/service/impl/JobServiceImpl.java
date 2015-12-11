package yourwebproject2.service.impl;

import yourwebproject2.framework.data.BaseJPAServiceImpl;
import yourwebproject2.model.entity.Job;
import yourwebproject2.model.repository.JobRepository;
import yourwebproject2.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Y.Kamesh on 8/2/2015.
 */
@Service
@Transactional
public class JobServiceImpl extends BaseJPAServiceImpl<Job, Long> implements JobService {
    private static Logger LOG = LoggerFactory.getLogger(JobServiceImpl.class);

    protected @Autowired
    JobRepository jobRepository;

    @PostConstruct
    public void setupService() {
        LOG.info("setting up jobService...");
        this.baseJpaRepository = jobRepository;
        this.entityClass = Job.class;
        this.baseJpaRepository.setupEntityClass(Job.class);
        LOG.info("jobService created...");
    }

    @Override
    public List<Job> fetchNewJobsToBeScheduledForExecutionPerPriority(int count) {
        return jobRepository.fetchNewJobsToBeScheduledForExecutionPerPriority(count);
    }

    @Override
    public List<Job> fetchFailedJobsToBeScheduledForExecutionPerPriority(int count) {
        return jobRepository.fetchFailedJobsToBeScheduledForExecutionPerPriority(count);
    }

    @Override
    public List<Job> fetchNewJobsToBeScheduledForExecutionPerSubmissionTimePriority(int count) {
        return jobRepository.fetchNewJobsToBeScheduledForExecutionPerSubmissionTimePriority(count);
    }

    @Override
    public List<Job> fetchFailedJobsToBeScheduledForExecutionPerSubmissionTimePriority(int count) {
        return jobRepository.fetchFailedJobsToBeScheduledForExecutionPerSubmissionTimePriority(count);
    }
}