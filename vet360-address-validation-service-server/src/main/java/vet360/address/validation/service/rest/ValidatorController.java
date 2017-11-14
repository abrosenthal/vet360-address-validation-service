package vet360.address.validation.service.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mdm.cuf.core.server.rest.provider.AbstractRestProvider;
import mdm.cuf.core.server.rest.provider.SwaggerCommon;
import mdm.cuf.person.bio.AddressBio;
import vet360.address.validation.service.ValidatorService;

@RequestMapping(value = ValidatorController.URL_PREFIX)
@RestController
@Api(tags = ValidatorController.TAG)
public class ValidatorController extends AbstractRestProvider {

    /** The version of this rest endpoint */
    protected static final String VERSION = "1";

    /** The URL prefix of this endpoint. */
    protected static final String URL_PREFIX = "/validator/v" + VERSION;

    /** the message keys file url */
    protected static final String MSG_KEYS_URL = "swagger/validator-v" + VERSION + "-msg-keys.html";
    
    /** the req's file url */
    protected static final String REQUIREMENTS_URL = "features/validator-v" + VERSION + "/index.html";
    
    /** The tag used in swagger documentation. */
    protected static final String TAG = "ValidatorBio-v" + VERSION;
    
    /** The description that shows up in swagger documentation. */
    protected static final String DESCRIPTION = "";
    
    @ExceptionHandler
    void handleHttpClientErrorException(HttpClientErrorException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
    
	@RequestMapping(value = "/validate", 
	        method=RequestMethod.POST, 
	        produces = {"application/json", "application/xml"})
	@ApiOperation(value = "Validate", notes = "Will validate address within the body")
	@ApiResponses(value = { @ApiResponse(code = 200, message = SwaggerCommon.MESSAGE_200) })
	public SpectrumCufResponse getResponse(@RequestBody SpectrumCufRequest cufRequest) {
	    SpectrumCufResponse cufResponse;
		AddressBio addressBio = (AddressBio) cufRequest.getBio();
		cufResponse = ValidatorService.validateCufAddress(addressBio);
	    return cufResponse;
	}
}
