package vet360.address.validation.test;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=vet360.address.validation.service.StubAddressValidatorApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class})
public class StubAddressValidatorApplicationTests {
	
	@Autowired
	private MockMvc mvc;

	@Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Spectrum Stub Service")));
    }
	
	@Test
	public void testInvalidCharError() throws Exception {
		 mvc.perform(MockMvcRequestBuilders.post("/validate").content("{ \"addressLine1\":\"555 Grove Lane\", "
		         + "\"city\" : \"Roswell\", \"stateCode\" : \"NY\", \"zip\" : \"30075\" }")
		         .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
         .andExpect(content().string(equalTo("Invalid Characters in Entry")));
	}
	
	@Test
	public void testEmptyMandatoryFieldError() throws Exception {
	    mvc.perform(MockMvcRequestBuilders.post("/validate").content("{ \"addressLine1\":\"555 Grove Lane\", "
                + "\"city\" : \"Roswell\", \"stateCode\" : \"CA\", \"zip\" : \"30075\" }")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
         .andExpect(content().string(equalTo("Mandatory Field is Empty")));
	}
	
	@Test
	public void testInvalidDateError() throws Exception {
	    mvc.perform(MockMvcRequestBuilders.post("/validate").content("{ \"addressLine1\":\"555 Grove Lane\", "
                + "\"city\" : \"Roswell\", \"stateCode\" : \"NV\", \"zip\" : \"30075\" }")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
         .andExpect(content().string(equalTo("Date for Entry is invalid")));
	}
	
	@Test
	public void testInvalidEntryTypeError() throws Exception {
	    mvc.perform(MockMvcRequestBuilders.post("/validate").content("{ \"addressLine1\":\"555 Grove Lane\", "
                + "\"city\" : \"Roswell\", \"stateCode\" : \"WY\", \"zip\" : \"30075\" }")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
         .andExpect(content().string(equalTo("Entry Must be Certain Type")));
	}
	
	@Test
	public void testMassiveEntryError() throws Exception {
	    mvc.perform(MockMvcRequestBuilders.post("/validate").content("{ \"addressLine1\":\"555 Grove Lane\", "
                + "\"city\" : \"Roswell\", \"stateCode\" : \"TX\", \"zip\" : \"30075\" }")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
         .andExpect(content().string(equalTo("Entry is too large")));
	}
	
	@Test
	public void testAddressValidationError() throws Exception {
	    mvc.perform(MockMvcRequestBuilders.post("/validate").content("{ \"addressLine1\":\"555 Grove Lane\", "
                + "\"city\" : \"Roswell\", \"stateCode\" : \"HI\", \"zip\" : \"30075\" }")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
         .andExpect(content().string(equalTo("Address Validation Failure")));
	}
	
	@Test
	public void testAddressValidationSuccess() throws Exception {
	    mvc.perform(MockMvcRequestBuilders.post("/validate").content("{ \"addressLine1\":\"555 Grove Lane\", "
                + "\"city\" : \"Roswell\", \"stateCode\" : \"GA\", \"zip\" : \"30075\" }")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
         .andExpect(content().string(equalTo("Address is valid")));
	}

}
