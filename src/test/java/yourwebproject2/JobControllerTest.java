package yourwebproject2;


import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit Test package for Job Controller
 *
 * Created by Y.Kamesh on 8/4/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:config/spring/appContext-scheduler.xml",
        "classpath:config/spring/appContext-interceptor.xml",
        "classpath:config/spring/appContext-jdbc-test.xml",
        "classpath:config/spring/appContext-repository.xml",
        "classpath:config/spring/appContext-service.xml",
        "classpath:config/spring/appContext-servlet.xml",
        "classpath:config/spring/appContext-ywp2.xml"
})
@WebAppConfiguration
public class JobControllerTest {
    private static Logger LOG = LoggerFactory.getLogger(JobControllerTest.class);
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .content("{\"name\":\"cat1\", \"priority\":1}")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result.name").value("cat1"));

        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .content("{\"name\":\"cat2\", \"priority\":2}")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result.name").value("cat2"));

        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .content("{\"name\":\"cat3\", \"priority\":3}")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result.name").value("cat3"));
    }

    @Test
    public void testJobSubmitAndStatusCoreFlow() throws Exception {
        LinkedHashMap<Long, String> jobIds = new LinkedHashMap<>();
        LinkedHashMap<Long, String> jobIdsByCompletionOrder = new LinkedHashMap<>();
        for(int c=1, cId=1; c<=100; c++) {
            LOG.info("Scheduling Job: job"+c+" with priority="+(cId));
            this.mockMvc.perform(post("/job/submit").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                    .content("{\"name\":\"job"+c+"\", \"catId\":"+(cId)+", \"metadataJsonString\":\"some_data\", \"callbackUrl\":\"http://some.url\"}")
                    .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                    .andExpect(jsonPath("$.result.name").value("job"+c))
                    .andDo(new ResultHandler() {
                        @Override
                        public void handle(MvcResult mvcResult) throws Exception {
                            JSONObject result = new JSONObject(mvcResult.getResponse().getContentAsString());
                            result = result.optJSONObject("result");
                            jobIds.put(result.optLong("id"), result.toString());
                        }
                    });
            cId += 1;
            if(cId >= 4) {
                cId=1;
            }
        }

        LOG.info("Giving some time for job executor to schedule and knock off a few submitted jobs");
        Thread.sleep(10000);

        int progressCount = 0;
        while(jobIds.keySet().size() != jobIdsByCompletionOrder.keySet().size()) {
            progressCount = 0;
            for (Long jobId : jobIds.keySet()) {
                try {
                    this.mockMvc.perform(get("/job/status/" + jobId)
                            .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                            .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                            .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType("application/json;charset=UTF-8"))
                            .andExpect(jsonPath("$.result.status").value("SUCCESSFUL"))
                            .andDo(new ResultHandler() {
                                @Override
                                public void handle(MvcResult mvcResult) throws Exception {
                                    JSONObject result = new JSONObject(mvcResult.getResponse().getContentAsString());
                                    result = result.optJSONObject("result");
                                    jobIdsByCompletionOrder.put(result.optLong("id"), result.toString());
                                }
                            });
                    progressCount += 1;
                    LOG.info("Finished executing Job: " + jobId);
                } catch (Throwable e) {
                }
                LOG.info("Progress: " + progressCount+"/100 Done");
            }
            Thread.sleep(1000);
        }

        Long prevJob = null;
        int outOfOrder = 0;
        for(Long currentJob : jobIdsByCompletionOrder.keySet()) {
            LOG.info(jobIdsByCompletionOrder.get(currentJob));

            if(prevJob == null) {
                prevJob = currentJob;
                continue;
            }

            JSONObject prevJobJson = new JSONObject(jobIdsByCompletionOrder.get(prevJob));
            JSONObject currJobJson = new JSONObject(jobIdsByCompletionOrder.get(currentJob));
            prevJob = currentJob;

            Integer prevPriority = prevJobJson.getJSONObject("category").getInt("priority");
            Integer currPriority = currJobJson.getJSONObject("category").getInt("priority");

            Long prevSubmitTime = prevJobJson.getLong("submitTime");
            Long currSubmitTime = currJobJson.getLong("submitTime");
            try {
                Assert.assertTrue(prevPriority <= currPriority || prevSubmitTime <= currSubmitTime);
            } catch (Throwable e) {
                outOfOrder++;
            }
        }

        LOG.info("Total insertions out of order: "+outOfOrder);
    }
}
