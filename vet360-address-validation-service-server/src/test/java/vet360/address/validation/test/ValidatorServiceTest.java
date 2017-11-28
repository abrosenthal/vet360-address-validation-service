package vet360.address.validation.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import vet360.address.validation.service.bio.ResponseAddress;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=vet360.address.validation.service.ValidatorApplication.class)
@AutoConfigureMockMvc(secure = false)

public class ValidatorServiceTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
  public void testDummyResponse() throws Exception {
       mvc.perform(MockMvcRequestBuilders.post("/validator/v1/dummy").content("{ \"addressLine1\":\"555 Grove Lane\", "
               + "\"city\" : \"Roswell\", \"stateCode\" : \"NY\", \"zip\" : \"30075\" }")
               .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }
	@Test
    public void testDeserialization() throws JsonProcessingException, IOException {
        String json = "{" +
                "\"Output\" : [ {" +
                "\"AddressLine1\" : \"576 AUDUBON ST\"," +
                    "\"StateProvince\" : \"LA\"," +
                    "\"PostalCode\" : \"70118-4950\"," +
                    "\"Confidence\" : \"100.00\"," +
                    "\"City\" : \"NEW ORLEANS\"," +
                    "\"Override\" : \"null\"," +
                    "\"AddressType\" : \"S\"," +
                    "\"user_fields\" : [ ]" +
                  "} ]" +
                "}";
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        JsonNode node = objectMapper.readTree(json);
        JsonNode addressNode = node.get("Output").get(0);
        ResponseAddress address = objectMapper.treeToValue(addressNode, ResponseAddress.class);
        Assert.assertNotNull(address);

        String deserializedJson = objectMapper.writeValueAsString(address);
        Assert.assertNotNull(deserializedJson);
        Assert.assertTrue("Address doesnt match expected", address.getAddressLine1().contains("576 AUDUBON ST"));
        Assert.assertTrue("Deserialized Json doesnt match expected", deserializedJson.contains("576 AUDUBON ST"));
    }

}
