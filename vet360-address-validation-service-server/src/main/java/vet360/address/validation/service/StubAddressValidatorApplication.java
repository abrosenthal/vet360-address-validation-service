package vet360.address.validation.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan("vet360.address.validation")
public class StubAddressValidatorApplication {
	
	@SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(StubAddressValidatorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StubAddressValidatorApplication.class, args);
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
