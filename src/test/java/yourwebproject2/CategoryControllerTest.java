package yourwebproject2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit Test package for Category Controller
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
public class CategoryControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCreateAndGetCategory() throws Exception {
        // Tests creation of a new category
        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .content("{\"name\":\"cat1\", \"priority\":1}")
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result.name").value("cat1"));

        // Tests creation of a new category with name present
        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .content("{\"name\":\"cat1\", \"priority\":1}")
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result").value("Category taken"));


        // Tests creation of a new category with priority present
        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .content("{\"name\":\"cat2\", \"priority\":1}")
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result").value("Priority taken"));


        // Tests creation of a another new category
        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .content("{\"name\":\"cat2\", \"priority\":2}")
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result.name").value("cat2"));

        // Tests get of an existing category
        this.mockMvc.perform(get("/category/getById/1")
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result.name").value("cat1"));

        // Tests get of non existing category
        this.mockMvc.perform(get("/category/getById/3")
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result").value("Not Found"));

        // Tests get of another existing category
        this.mockMvc.perform(get("/category/getById/2")
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result.name").value("cat2"));

        // Tests get of another existing category
        this.mockMvc.perform(get("/api/category/getById/2")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.result.name").value("cat2"));
    }

    @Test
    public void testCategoryValidations() throws Exception {
        // Tests validation of category input
        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .content("{\"name\":\"\", \"priority\":1}")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(2001));

        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .content("{\"name\":\"cat1\", \"priority\":}")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(2001));

        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .content("{\"categoryIncorrect\":{\"name\":\"\", \"priority\":1}}")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(2001));

        this.mockMvc.perform(post("/category/create").contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5a2FtZXNocmFvQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNDQ2NTA3ODk3fQ.ntqtsfZch9V51peXY-wzguRBECYUNuOul1DfJUiHcVY")
                .content("")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(2001));
    }

}
