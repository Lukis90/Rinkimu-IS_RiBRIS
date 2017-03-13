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
import lt.javainiai.model.PartyResultsEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RiBRIS_Application.class })
@DirtiesContext(classMode =  ClassMode.BEFORE_EACH_TEST_METHOD)
public class PartyResultsControllerIT {

    
    private static final String URI = "/party-results/";

    @Autowired
    private TestRestTemplate restTemplate;
    
    private static HttpHeaders headers = new HttpHeaders();
    
    @BeforeClass 
    public static void onlyOnce() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
     }
    
    @Test
    public void createPartyResultsAndCheckIfExist(){
        PartyResultsEntity results1 = new PartyResultsEntity();
    
        results1.setNumberOfVotes(5000L);
        createOrUpdatePartyResults(results1);
        
        List<PartyResultsEntity> results = getPartiesResults();
        Assert.assertThat(results.size(), is(1));         
    
    }
    
    
    
    
    private void createOrUpdatePartyResults(final PartyResultsEntity results){
        HttpEntity<PartyResultsEntity> entity =  
                new HttpEntity<PartyResultsEntity>(results, headers);
        ResponseEntity<String> response =
                restTemplate.exchange(URI, HttpMethod.POST, entity, String.class);
        
        Assert.assertThat(response.getStatusCode(), CoreMatchers.is(HttpStatus.CREATED));
   
    }
     
     
    private List<PartyResultsEntity> getPartiesResults(){
       ParameterizedTypeReference<List<PartyResultsEntity>> results = 
               new ParameterizedTypeReference<List<PartyResultsEntity>>() {};

       ResponseEntity<List<PartyResultsEntity>> response = 
               restTemplate.exchange(URI, HttpMethod.GET, null, results);

       Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
       return response.getBody();
   
    }
     
    @SuppressWarnings("unused")
	private void deletePartyResultsById(Long id){
       ResponseEntity<Void> response = 
               restTemplate.exchange(URI  + id, HttpMethod.DELETE, null, Void.class);
        
       Assert.assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    
    }
    
    @SuppressWarnings("unused")
    private PartyResultsEntity findPartyResultsById(Long id){
       ResponseEntity<PartyResultsEntity> response = 
               restTemplate.exchange(URI  + id, HttpMethod.GET, null, PartyResultsEntity.class);
         
       Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
       return response.getBody();
    
    }
    
}
