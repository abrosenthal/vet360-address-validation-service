package vet360.address.validation.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import mdm.cuf.person.bio.AddressBio;
import vet360.address.validation.hints.Hints;
import vet360.address.validation.service.dio.Address;
import vet360.address.validation.service.rest.SpectrumCufResponse;

public class ValidatorService {
    static Logger log = Logger.getLogger(ValidatorService.class) ;
    
    
    private ValidatorService() {}
    
    //Take in an AddressBio from a SpectrumCufRequest object, validate it, and return a SpectrumCufResponse object with Spectrum values
    public static SpectrumCufResponse validateCufAddress(AddressBio bio) {
        RestTemplate restTemplate = new RestTemplate();
        SpectrumCufResponse response = new SpectrumCufResponse();
        Map<Hints, String> hints;
        
        HttpEntity<String> entity = createHttpEntity();
        
        //Populate address from bio
        Address address = ValidatorConverter.mapBioToAddress(bio);

        //Call Spectrum UAM service 
        ResponseEntity<String> responseEntityAddress = restTemplate.exchange(createUrl(address), HttpMethod.GET, entity, String.class);
        
        //Transform Spectrum response into an Address object
        Address responseAddress = createResponseAddress(responseEntityAddress);
        
        //Populate AddressBio from Spectrum-returned Address 
        AddressBio responseBio = ValidatorConverter.mapAddressToBio(responseAddress);
        
        //Populate a Map with Spectrum values that do not map to the AddressBio 
        hints = ValidatorConverter.getHints(responseAddress);
        
        response.setBio(responseBio);
        response.setHints(hints);
        
        return response;
    }

    //Create HttpEntity that enables authentication for the Spectrum server
    private static HttpEntity<String> createHttpEntity() {
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

    //Map the Json coming from Spectrum to an Address object
    private static Address createResponseAddress(ResponseEntity<String> responseEntityAddress) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        Address responseAddress = null;
        String modifiedJson = ValidatorConverter.trimJson(responseEntityAddress.getBody());
        try {
            responseAddress = objectMapper.readValue(modifiedJson, Address.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return responseAddress;
    }


    //Create the URL containing the information for Spectrum
    private static String createUrl(Address address) {
        return "http://10.200.100.30:8080/rest/WebService_UAMwithsubflows/" + address.getOverride() + "/"
                + address.getAddressLine1() + "/" + address.getCity() +"/" + address.getStateProvince() + "/" 
                + address.getPostalCode() + "/" + address.getCountry() + "/results.json";
    }
    
    
}