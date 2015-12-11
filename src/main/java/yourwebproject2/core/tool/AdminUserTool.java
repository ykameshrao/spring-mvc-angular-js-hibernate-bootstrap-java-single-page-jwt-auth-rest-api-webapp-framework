package yourwebproject2.core.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: kameshr
 */
public class AdminUserTool {
    private static Logger LOG = LoggerFactory.getLogger(AdminUserTool.class);
    public static void main(String[] args) {
        LOG.info("Starting the Job scheduling and execution daemon.");
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:/config/spring/appContext-jdbc.xml",
                "classpath:/config/spring/appContext-repository.xml",
                "classpath:/config/spring/appContext-service.xml",
                "classpath:/config/spring/appContext-interceptor.xml"}, true);
        LOG.info("Loaded the context: " + ctx.getBeanDefinitionNames());
        LOG.info("Job scheduling and execution daemon started.");
    }
}
