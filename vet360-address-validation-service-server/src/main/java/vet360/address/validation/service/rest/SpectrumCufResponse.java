package vet360.address.validation.service.rest;

import org.springframework.stereotype.Component;

import mdm.cuf.core.api.CufResponse;
import vet360.address.validation.service.bio.ResponseAddress;

@Component
/** This class contains the validation results as well as the updated Address object, and will be returned as the response */
public class SpectrumCufResponse extends CufResponse {

    private static final long serialVersionUID = -4565182803683243695L;

    private ResponseAddress bio = new ResponseAddress();
    
    public ResponseAddress getBio() {
        return bio;
    }

    public void setBio(ResponseAddress bio) {
        this.bio = bio;
    }
}
