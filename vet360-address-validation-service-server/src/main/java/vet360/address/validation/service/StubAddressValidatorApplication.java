package vet360.address.validation.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

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

	public static void main(String[] args) throws Exception {
	    SpringApplication.run(StubAddressValidatorApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(StubAddressValidatorApplication.class);
    }
	
	public static String getResponseMessage(String stateCode) {
		String message = "";
		
		//Load properties file 
		String resourceName = "errors.properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		
		try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			props.load(resourceStream);
			//Check for error code; default to 'Address is valid'
			message = props.getProperty(stateCode, "Address is valid");
		}
		catch(FileNotFoundException e) {
			return "File Not Found";
		} catch (Exception e1) {

		    e1.printStackTrace();
		    return "An error occurred";

		}
		
		return message;
	}
}
