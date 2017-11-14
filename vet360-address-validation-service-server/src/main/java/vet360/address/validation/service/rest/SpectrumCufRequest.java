package vet360.address.validation.service.rest;

import org.springframework.stereotype.Component;

import mdm.cuf.core.api.CufResponse;
import mdm.cuf.person.bio.AddressBio;

@Component
public class SpectrumCufRequest extends CufResponse {

    private static final long serialVersionUID = -751681241680715523L;
    
    private AddressBio bio = new AddressBio();

    public AddressBio getBio() {
        return bio;
    }

    public void setBio(AddressBio bio) {
        this.bio = bio;
    }

}
