package vet360.address.validation.test;

import org.springframework.test.context.ActiveProfiles;

import mdm.cuf.core.server.AbstractMdmCufCoreServerSpringTest;
import mdm.cuf.core.server.MdmCufCoreServerProfilesAndQualifiers;

/**
 * This is the base test class for Spring tests within MDM CUF utilapp Server
 * 
 * @author jshrader
 */
@ActiveProfiles(MdmCufCoreServerProfilesAndQualifiers.MODE_UTIL_MICROSERVICE)
public abstract class ValidatorServerSpringTestBase extends AbstractMdmCufCoreServerSpringTest {
    
}
