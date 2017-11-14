package vet360.address.validation.service.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import mdm.cuf.core.api.CufResponse;
import mdm.cuf.person.bio.AddressBio;
import vet360.address.validation.hints.Hints;

@Component
public class SpectrumCufResponse extends CufResponse {

    private static final long serialVersionUID = -4565182803683243695L;

    private AddressBio bio = new AddressBio();
    
    private Map<Hints, String> hints = new HashMap<Hints, String>();

    public AddressBio getBio() {
        return bio;
    }

    public void setBio(AddressBio bio) {
        this.bio = bio;
    }
    
    public Map<Hints, String> getHints() {
        return hints;
    }

    public void setHints(Map<Hints, String> hints) {
        this.hints = hints;
    }
}
