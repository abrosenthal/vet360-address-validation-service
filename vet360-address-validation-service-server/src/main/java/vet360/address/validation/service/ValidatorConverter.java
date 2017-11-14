package vet360.address.validation.service;

import java.util.EnumMap;
import java.util.Map;

import mdm.cuf.person.bio.AddressBio;
import vet360.address.validation.hints.Hints;
import vet360.address.validation.service.dio.Address;

public class ValidatorConverter {
    
    /** Overriding public constructor */
    private ValidatorConverter() {}

    //Convert and AddressBio to an Address
    public static Address mapBioToAddress(AddressBio bio) {
        Address address = new Address();
        address.setAddressLine1(bio.getStreet1());
        address.setAddressLine2(bio.getStreet2());
        address.setCity(bio.getCityName());
        address.setStateProvince(bio.getStateCode());
        address.setCountry(bio.getCountryName());
        address.setPostalCode(bio.getZipCode());
        
        return address;
    }

  //Convert and Address to an AddressBio
    public static AddressBio mapAddressToBio(Address responseAddress) {
        AddressBio bio = new AddressBio();
        bio.setStreet1(responseAddress.getAddressLine1());
        bio.setStreet2(responseAddress.getAddressLine2());
        bio.setCityName(responseAddress.getCity());
        bio.setStateCode(responseAddress.getStateProvince());
        bio.setCountryName(responseAddress.getCountry());
        bio.setZipCode(responseAddress.getPostalCode());
        
        return bio;
    }

    //Populate Map of Hints from an Address to supplement the AddressBio
    public static Map<Hints, String> getHints(Address responseAddress) {
        EnumMap<Hints, String> hints = new EnumMap<>(Hints.class);
        
        hints.put(Hints.CONFIDENCE, responseAddress.getConfidence());
        hints.put(Hints.DPV, responseAddress.getDpv());
        hints.put(Hints.RDI, responseAddress.getRdi());
        
        return hints;
    }
    
    public static String trimJson(String json) {
        json = json.replaceFirst("\\\"Output\\\" : \\[ \\{", "");
        return json.replaceFirst("\\} \\]", "");
    }

}
