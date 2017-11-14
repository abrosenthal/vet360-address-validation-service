package vet360.address.validation.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Import(ValidatorServerConfig.class)
@ComponentScan(basePackages = "vet360.address.validation", excludeFilters = @Filter(Configuration.class))
public class StubAddressValidatorApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
	    SpringApplication.run(StubAddressValidatorApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(StubAddressValidatorApplication.class);
    }
	
}
