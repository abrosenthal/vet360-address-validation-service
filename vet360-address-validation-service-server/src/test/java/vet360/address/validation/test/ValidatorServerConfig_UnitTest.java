package vet360.address.validation.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mdm.cuf.core.server.MdmCufCoreServerProperties;


/**
 * This Config Unit Test is a simple test to ensure the spring context loads for this project.
 * 
 * @author randy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=vet360.address.validation.service.StubAddressValidatorApplication.class)
@AutoConfigureMockMvc
public class ValidatorServerConfig_UnitTest {

//	@Autowired
//	private MdmCufCoreServerProperties properties;
	
	
//	@Test
//	public void contextLoads() {
//		Assert.assertNotNull(properties);
//	}

}
