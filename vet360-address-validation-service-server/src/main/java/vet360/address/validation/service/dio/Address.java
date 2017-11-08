package vet360.address.validation.service.dio;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import mdm.cuf.core.api.ServiceResponse;

@Component
@ConfigurationProperties(prefix = "vet360.address.validation")
public class Address extends ServiceResponse {

	/** The Constant serialVersionUID.  */
    private static final long serialVersionUID = -5020186022423349715L;
    
    private String addressLine1;
	private String city;
	private String stateCode;
	private int zip;
	
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	
}
