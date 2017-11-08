package vet360.address.validation.service.rest;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import mdm.cuf.core.server.MdmCufCoreServerProperties;
import mdm.cuf.core.server.rest.provider.SwaggerCommon;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class ValidatorRestProviderConfig {
    
    @Autowired 
    private MdmCufCoreServerProperties properties;
    
    @Bean
    public Docket cufReferenceDataV1RestApi() {

        //params used within the docket
        String group = "Reference Data";
        String version = "v1";
        String groupName = group + version;
        String msgKeysUrl = "";
        String urlPrefix = ValidatorController.URL_PREFIX;
        String requirementsUrl = ValidatorController.REQUIREMENTS_URL;
        
        //populate any global parameters
        List<Parameter> globalParameters = SwaggerCommon.getCoreGlobalParameters(properties);
        
        //build the docket
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(
                        new ApiInfoBuilder()
                        .title("The CUF " + groupName + " Collection of REST Endpoints")
                        .version(version)
                        .description("<a href=\"" + msgKeysUrl + "\">API Message Keys</a>&nbsp;&nbsp;<a href=\"" + requirementsUrl + "\">Gherkin Requirements</a>"
                         )
                        .build()
                 )
                .select()
                .paths(regex(urlPrefix + "/.*"))
                .build()
                .globalOperationParameters(globalParameters)
                .ignoredParameterTypes(ApiIgnore.class)
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(true)
                .tags(new Tag(ValidatorController.TAG, ValidatorController.DESCRIPTION));
    }
    

}
