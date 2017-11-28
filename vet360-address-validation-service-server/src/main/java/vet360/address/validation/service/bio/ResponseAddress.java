package vet360.address.validation.service.bio;
        

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import mdm.cuf.core.api.ServiceResponse;

@Component
@ConfigurationProperties(prefix = "vet360.address.validation")
/** This class represents the Address with all of the fields that are returned from Spectrum's UAM */
public class ResponseAddress extends ServiceResponse {
    
    /** Overriding public constructor */
    public ResponseAddress() {}
    

    /** The Constant serialVersionUID.  */
    private static final long serialVersionUID = -5020186022423349715L;
    
    //Source fields
    private int vet360ID;
    private int sourceSysID;
    private Date deceasedDate;
    private String originSourceSys;
    private String sourceSysUser;
    private Date sourceSysDate;
    private Date confirmationDate;
    private String override;
    private String[] user_fields;
    
    //BSM fields
    private boolean overrideIndicator;
    private String newSourceSys;
    private Date newSourceSysDate;
    private String dataSteward;
    private String notes;
    private Date sourceDate;
    private String supervisor;
    private Date overrideApprovalDate;
    
    //Address fields
    private String addressPOU;
    private String addressType;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
	private String city; 
	private String stateProvince;
	private String postalCode;
	private String country;
	
	//Geocoding
	private String countryName;
	private int fips2;
	private int iso2;
	private int iso3;
	private int latitude;
	private int longitude;
	private int geocodePrecision;
	private Date geocodeCalcDate;
	
	//UAM Validation results
	private String dpv;
	private String rdi;
	private String confidence;
	private String additionalInput;
	
	
	//Spectrum result codes
	HashMap<String, String> spectrumResultCodes = new HashMap<>();
	
    public int getVet360ID() {
        return vet360ID;
    }
    public void setVet360ID(int vet360id) {
        vet360ID = vet360id;
    }
    public int getSourceSysID() {
        return sourceSysID;
    }
    public void setSourceSysID(int sourceSysID) {
        this.sourceSysID = sourceSysID;
    }
    public Date getDeceasedDate() {
        return deceasedDate;
    }
    public void setDeceasedDate(Date deceasedDate) {
        this.deceasedDate = deceasedDate;
    }
    public String getOriginSourceSys() {
        return originSourceSys;
    }
    public void setOriginSourceSys(String originSourceSys) {
        this.originSourceSys = originSourceSys;
    }
    public String getSourceSysUser() {
        return sourceSysUser;
    }
    public void setSourceSysUser(String sourceSysUser) {
        this.sourceSysUser = sourceSysUser;
    }
    public Date getSourceSysDate() {
        return sourceSysDate;
    }
    public void setSourceSysDate(Date sourceSysDate) {
        this.sourceSysDate = sourceSysDate;
    }
    public Date getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
    public boolean isOverrideIndicator() {
        return overrideIndicator;
    }
    public void setOverrideIndicator(boolean overrideIndicator) {
        this.overrideIndicator = overrideIndicator;
    }
    public String getNewSourceSys() {
        return newSourceSys;
    }
    public void setNewSourceSys(String newSourceSys) {
        this.newSourceSys = newSourceSys;
    }
    public Date getNewSourceSysDate() {
        return newSourceSysDate;
    }
    public void setNewSourceSysDate(Date newSourceSysDate) {
        this.newSourceSysDate = newSourceSysDate;
    }
    public String getDataSteward() {
        return dataSteward;
    }
    public void setDataSteward(String dataSteward) {
        this.dataSteward = dataSteward;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public Date getSourceDate() {
        return sourceDate;
    }
    public void setSourceDate(Date sourceDate) {
        this.sourceDate = sourceDate;
    }
    public String getSupervisor() {
        return supervisor;
    }
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
    public Date getOverrideApprovalDate() {
        return overrideApprovalDate;
    }
    public void setOverrideApprovalDate(Date overrideApprovalDate) {
        this.overrideApprovalDate = overrideApprovalDate;
    }
    public String getAddressPOU() {
        return addressPOU;
    }
    public void setAddressPOU(String addressPOU) {
        this.addressPOU = addressPOU;
    }
    public String getAddressType() {
        return addressType;
    }
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
        return addressLine2;
    }
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    public String getAddressLine3() {
        return addressLine3;
    }
    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateProvince() {
        return stateProvince;
    }
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public int getFips2() {
        return fips2;
    }
    public void setFips2(int fips2) {
        this.fips2 = fips2;
    }
    public int getIso2() {
        return iso2;
    }
    public void setIso2(int iso2) {
        this.iso2 = iso2;
    }
    public int getIso3() {
        return iso3;
    }
    public void setIso3(int iso3) {
        this.iso3 = iso3;
    }
    public int getLatitude() {
        return latitude;
    }
    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
    public int getLongitude() {
        return longitude;
    }
    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
    public int getGeocodePrecision() {
        return geocodePrecision;
    }
    public void setGeocodePrecision(int geocodePrecision) {
        this.geocodePrecision = geocodePrecision;
    }
    public Date getGeocodeCalcDate() {
        return geocodeCalcDate;
    }
    public void setGeocodeCalcDate(Date geocodeCalcDate) {
        this.geocodeCalcDate = geocodeCalcDate;
    }
    public String getDpv() {
        return dpv;
    }
    public void setDpv(String dpv) {
        this.dpv = dpv;
    }
    public String getRdi() {
        return rdi;
    }
    public void setRdi(String rdi) {
        this.rdi = rdi;
    }
    public String getConfidence() {
        return confidence;
    }
    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
    public String getAdditionalInput() {
        return additionalInput;
    }
    public void setAdditionalInput(String additionalInput) {
        this.additionalInput = additionalInput;
    }
    public Map<String, String> getSpectrumResultCodes() {
        return spectrumResultCodes;
    }
    public void setSpectrumResultCodes(Map<String, String> spectrumResultCodes) {
        this.spectrumResultCodes = (HashMap<String, String>) spectrumResultCodes;
    }
    public String getOverride() {
        return override;
    }
    public void setOverride(String override) {
        this.override = override;
    }
    public String[] getUser_fields() {
        return user_fields;
    }
    public void setUser_fields(String[] user_fields) {
        this.user_fields = user_fields;
    }
}
