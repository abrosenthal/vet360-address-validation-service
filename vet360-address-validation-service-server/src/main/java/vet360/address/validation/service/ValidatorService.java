package vet360.address.validation.service;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import vet360.address.validation.service.bio.RequestAddress;
import vet360.address.validation.service.bio.ResponseAddress;
import vet360.address.validation.service.rest.SpectrumCufResponse;
import vet360.address.validation.service.rest.ValidatorServerProperties;

@Service
/** This class contains the business logic in order to send the Address request to Spectrum's UAM and return the response to the sender */
public class ValidatorService {
    
    @Autowired
    ValidatorServerProperties properties; 
    
    static Logger log = Logger.getLogger(ValidatorService.class) ;
    
    /** Overriding public constructor */
    private ValidatorService() {}
    
    /** Take in an RequestAddress from a SpectrumCufRequest object, validate it with Spectrum's UAM, and return a SpectrumCufResponse object 
     *  with UAM result values 
     * */
    public SpectrumCufResponse validateCufAddress(RequestAddress requestAddress) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntityAddress;
        ResponseAddress responseAddress; 
        SpectrumCufResponse response = new SpectrumCufResponse();
        HttpEntity<String> entity = createHttpEntity();

        //Call Spectrum's UAM and get the response
        try {
            responseEntityAddress = restTemplate.exchange(createUrl(requestAddress), HttpMethod.GET, entity, String.class);
        }
        catch(HttpClientErrorException e) {
           throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The input data is invalid");
        }
        
        responseAddress = createResponseAddress(responseEntityAddress);
        response.setBio(responseAddress);
        
        return response;
    }

    /** Create HttpEntity that provides user credentials for the Spectrum UAM server */
    private HttpEntity<String> createHttpEntity() {
        String plainCreds = "vet360:guest";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return entity;
    }

    /** Map the Json coming from Spectrum's UAM to a ResponseAddress object */
    private ResponseAddress createResponseAddress(ResponseEntity<String> responseEntityAddress) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        ResponseAddress responseAddress = new ResponseAddress();
        System.out.println(responseEntityAddress.getBody());
        try {
            JsonNode node = objectMapper.readTree(responseEntityAddress.getBody());
            JsonNode addressNode = node.get("Output").get(0);
            responseAddress = objectMapper.treeToValue(addressNode, ResponseAddress.class);
            String deserializedJson = objectMapper.writeValueAsString(responseAddress);
            responseAddress = objectMapper.readValue(deserializedJson, ResponseAddress.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return responseAddress;
    }


    /** Create the URL containing the information for Spectrum's UAM */
    private String createUrl(RequestAddress address) {
        return properties.getValidationServiceUrl() + properties.getUamServiceUrl() + "/" + address.getOverride() + "/"
                + address.getStreet1() + "/" + address.getCityName() +"/" + address.getStateCode() + "/" 
                + address.getZipCode() + "/" + address.getCountryName() + "/results.json";
    }
    
    
}