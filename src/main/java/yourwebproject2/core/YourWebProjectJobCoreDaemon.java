package yourwebproject2.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main class to run job execution logic in a demon mode
 *
 * Created by Y.Kamesh on 8/8/2015.
 */
public class YourWebProjectJobCoreDaemon {
    private static Logger LOG = LoggerFactory.getLogger(YourWebProjectJobCoreDaemon.class);
    public static void main(String[] args) {
        LOG.info("Starting the Job scheduling and execution daemon.");
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:/config/spring/appContext-jdbc.xml",
                                                                                "classpath:/config/spring/appContext-repository.xml",
                                                                                "classpath:/config/spring/appContext-service.xml",
                                                                                "classpath:/config/spring/appContext-scheduler.xml"}, true);
        LOG.info("Loaded the context: " + ctx.getBeanDefinitionNames());
        LOG.info("Job scheduling and execution daemon started.");
    }
}
