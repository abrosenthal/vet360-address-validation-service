package vet360.address.validation.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import mdm.cuf.person.bio.AddressBio;
import vet360.address.validation.service.rest.SpectrumCufResponse;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=vet360.address.validation.service.ValidatorApplication.class)
@AutoConfigureMockMvc(secure = false)
public class ValidatorResponseTest {
    
    AddressBio bio = new AddressBio();
    
    @Autowired
    SpectrumCufResponse response;

    @Test
    public void testNotNullAddress() throws Exception {
         Assert.notNull(response.getBio(), "Bio must not be null");
    }
}
