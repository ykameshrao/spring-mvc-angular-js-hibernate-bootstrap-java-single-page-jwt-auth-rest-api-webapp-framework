package yourwebproject2.core;

import yourwebproject2.model.entity.Job;
import yourwebproject2.service.JobService;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author: kameshr
 */
public abstract class AbstractJobSchedulingWorker {
    void processResults(Map<Future<Job>, Job> result, JobService jobService, Logger LOG) {
        for(Future<Job> jobFuture : result.keySet()) {
            try {
                Job resultJob = jobFuture.get();
                LOG.info("Job Status: name="+resultJob.getName()+" status="+resultJob.getStatus());
                jobService.update(jobFuture.get());
            } catch (Exception e) {
                e.printStackTrace();
                Job failedJob = result.get(jobFuture);
                failedJob.setStatus(Job.Status.FAILED);
                try {
                    jobService.update(failedJob);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
