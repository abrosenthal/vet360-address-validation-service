package vet360.address.validation.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mdm.cuf.person.bio.AddressBio;
import vet360.address.validation.service.ValidatorConverter;
import vet360.address.validation.service.dio.Address;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=vet360.address.validation.service.StubAddressValidatorApplication.class)
@AutoConfigureMockMvc(secure = false)
public class TestValidatorConverter {

    
    @Test
    public void testBioToAddress() {
        AddressBio bio = new AddressBio();
        bio.setStreet1("2900 S Quincy St");
        bio.setStreet2("");
        bio.setCityName("Arlington");
        bio.setStateCode("VA");
        bio.setCountryName("USA");
        bio.setZipCode("22206");
        
        Address address = ValidatorConverter.mapBioToAddress(bio);
        
        Assert.assertTrue("Address line 1 doesnt match", bio.getStreet1().equals(address.getAddressLine1()));
        Assert.assertTrue("Address line 2 doesnt match", bio.getStreet2().equals(address.getAddressLine2()));
        Assert.assertTrue("City doesnt match", bio.getCityName().equals(address.getCity()));
        Assert.assertTrue("State doesnt match", bio.getStateCode().equals(address.getStateProvince()));
        Assert.assertTrue("Country name doesnt match", bio.getCountryName().equals(address.getCountry()));
        Assert.assertTrue("Zip doesnt match", bio.getZipCode().equals(address.getPostalCode()));
    }
    
    @Test
    public void testAddressToBio() {
        Address address = new Address();
        address.setAddressLine1("2900 S Quincy St");
        address.setAddressLine2("");
        address.setCity("Arlington");
        address.setStateProvince("VA");
        address.setCountry("USA");
        address.setPostalCode("22206");
        
        AddressBio bio = ValidatorConverter.mapAddressToBio(address);
        
        Assert.assertTrue("Address line 1 doesnt match", address.getAddressLine1().equals(bio.getStreet1()));
        Assert.assertTrue("Address line 2 doesnt match", address.getAddressLine2().equals(bio.getStreet2()));
        Assert.assertTrue("City doesnt match", address.getCity().equals(bio.getCityName()));
        Assert.assertTrue("State doesnt match", address.getStateProvince().equals(bio.getStateCode()));
        Assert.assertTrue("Country name doesnt match", address.getCountry().equals(bio.getCountryName()));
        Assert.assertTrue("Zip doesnt match", address.getPostalCode().equals(bio.getZipCode()));
    }
    
    @Test
    public void testDeserialization() {
        String json = "{" +
                "\"Output\" : [ {" +
                    "\"AddressLine1\" : \"576 AUDUBON ST\"," +
                    "\"StateProvince\" : \"LA\"," +
                    "\"PostalCode\" : \"70118-4950\"," +
                    "\"Confidence\" : \"100.00\"," +
                    "\"City\" : \"NEW ORLEANS\"," +
                    "\"Override\" : \"null\"," +
                    "\"AddressType\" : \"S\"," +
                    "\"user_fields\" : [ ]" +
                  "} ]" +
                "}";
        
        String expectedJson = "{" +
                "" +
                "\"AddressLine1\" : \"576 AUDUBON ST\"," +
                "\"StateProvince\" : \"LA\"," +
                "\"PostalCode\" : \"70118-4950\"," +
                "\"Confidence\" : \"100.00\"," +
                "\"City\" : \"NEW ORLEANS\"," +
                "\"Override\" : \"null\"," +
                "\"AddressType\" : \"S\"," +
                "\"user_fields\" : [ ]" +
              "" +
            "}";
        String deserializedJson = ValidatorConverter.trimJson(json);
        Assert.assertEquals("Deserialized Json doesnt match expected", expectedJson, deserializedJson);
    }
}
