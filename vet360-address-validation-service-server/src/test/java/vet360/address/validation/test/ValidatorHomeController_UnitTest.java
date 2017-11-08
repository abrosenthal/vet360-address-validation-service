package vet360.address.validation.test;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Tests the stub address validator home controller redirect as desired.
 * 
 * @author randy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=vet360.address.validation.service.StubAddressValidatorApplication.class)
@AutoConfigureMockMvc
public class ValidatorHomeController_UnitTest {

    /** The rest template. */
    @Autowired
    private MockMvc mvc;

    /**
     * Home controller test.
     */
    @Ignore
    @Test
    public void testController() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("{\"messages\":[],\"addressLine1\":"
                + "\"hello world\",\"city\":\"City: null\",\"zip\":0}")));
                    
       
        
        
        //using rest-assured, test the root url
//        given().port(8080).basePath("/").when().get("").then()
//            .statusCode(200)
//            .and()
//            .body("html.head.title", equalTo("Swagger UI"));

        //using the TestRestTemplate, ensure we get the redirect
//       HttpHeaders headers = restTemplate.getForEntity("/", String.class).getHeaders();
//       Assert.assertTrue(headers.getLocation().toString().contains("/swagger-ui.html"));
    }
    
}
