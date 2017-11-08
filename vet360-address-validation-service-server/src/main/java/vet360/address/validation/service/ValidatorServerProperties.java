package vet360.address.validation.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import mdm.cuf.core.server.AbstractMdmCufCoreServerProperties;

@Component
@ConfigurationProperties(prefix = "vet360.address.validation")
public class ValidatorServerProperties extends AbstractMdmCufCoreServerProperties {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -901411920058806029L;
    
    private String demoPropertyOne;

    public String getDemoPropertyOne() {
        return demoPropertyOne;
    }

    public void setDemoPropertyOne(final String demoPropertyOne) {
        this.demoPropertyOne = demoPropertyOne;
    }

}
