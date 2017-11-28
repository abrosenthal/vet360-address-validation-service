package vet360.address.validation.service.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import mdm.cuf.core.server.AbstractMdmCufCoreServerProperties;

/**
 * Application properties, they can be wired up by using address-validation placeholder in yml
 *
 * @author randy
 */

/** Class containing the properties found in the .yml file */
@Component
@ConfigurationProperties(prefix = "address-validation")
public class ValidatorServerProperties extends AbstractMdmCufCoreServerProperties {

    private static final long serialVersionUID = -901411920058806029L;
    
    private String validationServiceUrl;
    private String UamServiceUrl;

    public String getValidationServiceUrl() {
        return validationServiceUrl;
    }

    public void setValidationServiceUrl(final String validationServiceUrl) {
        this.validationServiceUrl = validationServiceUrl;
    }

    public String getUamServiceUrl() {
        return UamServiceUrl;
    }

    public void setUamServiceUrl(String uamServiceUrl) {
        UamServiceUrl = uamServiceUrl;
    }

}
