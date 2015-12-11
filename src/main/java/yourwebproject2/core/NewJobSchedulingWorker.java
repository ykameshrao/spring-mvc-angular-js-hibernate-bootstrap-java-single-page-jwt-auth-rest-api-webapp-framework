package yourwebproject2.core;

import yourwebproject2.model.entity.Job;
import yourwebproject2.model.entity.helper.CategoryPriorityComparator;
import yourwebproject2.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Y.Kamesh on 8/5/2015.
 */
public class NewJobSchedulingWorker extends AbstractJobSchedulingWorker {
    private static Logger LOG = LoggerFactory.getLogger(NewJobSchedulingWorker.class);

    @Autowired
    private JobService jobService;

    private ExecutorService executorService = Executors.newFixedThreadPool(50);

    /**
     * Runs every five minutes.
     */
    @Scheduled(initialDelay = 5000L, fixedRate = 5 * 60 * 1000L)
    public void scheduleNewJobsForExecution() {
        LOG.info("Fetching new jobs as per category and submit time priority...");
        List<Job> newJobs = jobService.fetchNewJobsToBeScheduledForExecutionPerSubmissionTimePriority(10);
        LOG.info("Fetched Jobs Count: "+newJobs.size());
        Collections.sort(newJobs, new CategoryPriorityComparator());

        Map<Future<Job>, Job> result = new LinkedHashMap<>();
        for(Job newJob : newJobs) {
            try {
                LOG.info("Scheduling Job: name="+newJob.getName()+", priority="+newJob.getCategory().getPriority()
                        +", submitTime="+newJob.getSubmitTime());
                newJob.setScheduledTime(new Date(System.currentTimeMillis()));
                newJob.setStatus(Job.Status.PRIORITIZED);
                jobService.update(newJob);
                result.put(executorService.submit(new JobExecutionThread(newJob)), newJob);
                LOG.info("Scheduled Job: name="+newJob.getName()+", priority="+newJob.getCategory().getPriority()
                        +", scheduledTime="+newJob.getScheduledTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LOG.info("Done scheduling the jobs");
        LOG.info("Waiting on results of the jobs");

        LOG.info("Fetching results of the jobs");

        processResults(result, jobService, LOG);
    }
}
