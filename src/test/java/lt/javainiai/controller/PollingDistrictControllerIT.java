package lt.javainiai.controller;

import static org.hamcrest.CoreMatchers.is;


import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import lt.javainiai.RiBRIS_Application;
import lt.javainiai.model.PollingDistrictEntity;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RiBRIS_Application.class })
@DirtiesContext(classMode =  ClassMode.BEFORE_EACH_TEST_METHOD)
public class PollingDistrictControllerIT {
    
    
    private static final String URI = "/polling-districts/";

    @Autowired
    private TestRestTemplate restTemplate;
    
    private static HttpHeaders headers = new HttpHeaders();
    
    @BeforeClass 
    public static void onlyOnce() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
     }
     
    @Test
    public void createPollingDistrictAndCheckIfExist(){
        PollingDistrictEntity pollingDistrict = new PollingDistrictEntity();
        pollingDistrict.setName("Senamiescio apl.");
        pollingDistrict.setNumOfVoters(5000L);
        pollingDistrict.setAddress("Gedimino pr. 45-20");
        createOrUpdatePollingDistrict(pollingDistrict);
        
        
        List<PollingDistrictEntity> pollingDistricts = getPollingDistricts();
        Assert.assertThat(pollingDistricts.size(), is(1));
       
    }
    
    
    @Test
    public void findPollingDistrictsById(){
        PollingDistrictEntity pollingDistrict1 = new PollingDistrictEntity();
        pollingDistrict1.setName("Senamiescio apl.");
        pollingDistrict1.setNumOfVoters(5000L);
        pollingDistrict1.setAddress("Gedimino pr. 45-20");

        PollingDistrictEntity pollingDistrict2 = new PollingDistrictEntity();
        pollingDistrict2.setName("Naujamiescio apl.");
        pollingDistrict2.setNumOfVoters(3300L);
        pollingDistrict2.setAddress("Savanoriu pr. 23-69");
        
        createOrUpdatePollingDistrict(pollingDistrict1);
        createOrUpdatePollingDistrict(pollingDistrict2);
             
        Assert.assertThat((findPollingDistrictById(2L)).getName(), is("Naujamiescio apl."));
        Assert.assertThat((findPollingDistrictById(1L)).getName(), is("Senamiescio apl."));
        
    }
    
    @Test
    public void updatePollingDistrict() {     
        PollingDistrictEntity pollingDistrict1 = new PollingDistrictEntity();
        pollingDistrict1.setName("Senamiescio apl.");
        pollingDistrict1.setNumOfVoters(5000L);
        pollingDistrict1.setAddress("Gedimino pr. 45-20");
        
        createOrUpdatePollingDistrict(pollingDistrict1);
        
        pollingDistrict1.setId(1L);
        pollingDistrict1.setAddress("Gerosios Vilties g. 45-1");
        pollingDistrict1.setNumOfVoters(3000L);
        
        createOrUpdatePollingDistrict(pollingDistrict1);
          
        Assert.assertEquals("Senamiescio apl.", 
                (findPollingDistrictById(1L)).getName());
        Assert.assertEquals("Gerosios Vilties g. 45-1", 
                (findPollingDistrictById(1L)).getAddress());
        Assert.assertEquals(pollingDistrict1.getNumOfVoters(), 
                (findPollingDistrictById(1L)).getNumOfVoters());
        
        List<PollingDistrictEntity> pollingDistricts = getPollingDistricts();
        Assert.assertThat(pollingDistricts.size(), is(1));    
              
    }
    
    @Test
    public void createPollingDistrictAndCheckIfDeleteWorks(){
        PollingDistrictEntity pollingDistrict1 = new PollingDistrictEntity();
        pollingDistrict1.setName("Senamiescio apl.");
        pollingDistrict1.setNumOfVoters(5000L);
        pollingDistrict1.setAddress("Gedimino pr. 45-20");
        
        createOrUpdatePollingDistrict(pollingDistrict1);
    
    
        List<PollingDistrictEntity> pollingDistricts = getPollingDistricts();
        Assert.assertThat(pollingDistricts.size(), is(1));
        deletePollingDistrictById(1L);
        
        pollingDistricts = getPollingDistricts();
        Assert.assertThat(pollingDistricts.size(), is(0));
    }
    
    
    
    private void createOrUpdatePollingDistrict(final PollingDistrictEntity pollingDistrict){
        HttpEntity<PollingDistrictEntity> entity =
                new HttpEntity<PollingDistrictEntity>(pollingDistrict, headers);
        ResponseEntity<String> response = 
                restTemplate.exchange(URI, HttpMethod.POST, entity, String.class);
        
        Assert.assertThat("Create PollingDistrict:",response.getStatusCode(),
                CoreMatchers.is(HttpStatus.CREATED));
     }
    
    private List<PollingDistrictEntity> getPollingDistricts(){
        
        ParameterizedTypeReference<List<PollingDistrictEntity>> pollingDistricts = 
                new ParameterizedTypeReference<List<PollingDistrictEntity>>() {};

        ResponseEntity<List<PollingDistrictEntity>> response =
                restTemplate.exchange(URI, HttpMethod.GET, null, pollingDistricts);
        
        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
        return response.getBody();
      }
    
    private void deletePollingDistrictById(Long id){
        
        ResponseEntity<Void> response = restTemplate.exchange(URI  + id,
                HttpMethod.DELETE, null, Void.class);
       
        Assert.assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        
    }
    
    private PollingDistrictEntity findPollingDistrictById(Long id){
        ResponseEntity<PollingDistrictEntity> response =
                restTemplate.exchange(URI + id, HttpMethod.GET, null,
                        PollingDistrictEntity.class);
   
        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
        return response.getBody();
    
    }
    
}
