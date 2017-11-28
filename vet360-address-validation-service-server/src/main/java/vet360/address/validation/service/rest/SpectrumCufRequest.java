package vet360.address.validation.service.rest;

import org.springframework.stereotype.Component;

import mdm.cuf.core.api.CufRequest;
import vet360.address.validation.service.bio.RequestAddress;

@Component
/** This class will contain the Address to be validated with Spectrum's UAM */
public class SpectrumCufRequest extends CufRequest {

    private static final long serialVersionUID = -751681241680715523L;
    
    private RequestAddress address = new RequestAddress();

    public RequestAddress getAddress() {
        return address;
    }

    public void setAddress(RequestAddress address) {
        this.address = address;
    }

}
