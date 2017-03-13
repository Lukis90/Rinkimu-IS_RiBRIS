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
import lt.javainiai.model.PartyEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RiBRIS_Application.class })
@DirtiesContext(classMode =  ClassMode.BEFORE_EACH_TEST_METHOD)
public class PartyControllerIT {

    private static final String URI = "/parties/";

    @Autowired
    private TestRestTemplate restTemplate;
    
    public static HttpHeaders headers = new HttpHeaders();
    
    @BeforeClass 
    public static void onlyOnce() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
     }
    
    @Test
    public void createPartyAndCheckIfExist(){
        PartyEntity party = new PartyEntity();
        party.setName("Darbo partija");
        party.setPartyNo(88L);
        
        createOrUpdateParty(party);
        
        List<PartyEntity> parties= getParties();
        Assert.assertThat(parties.size(), is(1));
    }
    
    @Test
    public void findPartyById(){
        PartyEntity party1 = new PartyEntity();
        party1.setName("Darbo partija");
        party1.setPartyNo(88L);
        
        PartyEntity party2 = new PartyEntity();
        party2.setName("Liberalu partija");
        party2.setPartyNo(07L);
        
        createOrUpdateParty(party1);
        createOrUpdateParty(party2);
        
        
        Assert.assertThat((findCandidateById(2L)).getName(), is("Liberalu partija"));
        Assert.assertThat((findCandidateById(1L)).getName(), is("Darbo partija"));
        
    }
    
    @Test
    public void updateParty(){
        PartyEntity party1 = new PartyEntity();
        party1.setName("Darbo partija");
        party1.setPartyNo(88L);
        
        createOrUpdateParty(party1);
        
        party1.setName("Liberalu partija");
        party1.setId(1L);
        createOrUpdateParty(party1);
        
        
        Assert.assertEquals("Liberalu partija", (findCandidateById(1L)).getName());
        
        List<PartyEntity> parties = getParties();
        Assert.assertThat(parties.size(), is(1));
        
    }

      
    @Test
    public void createPartyAndCheckIfDeleteWorks(){
        PartyEntity party1 = new PartyEntity();
        party1.setName("Darbo partija");
        party1.setPartyNo(88L);
        
        createOrUpdateParty(party1);
        
        List<PartyEntity> parties = getParties();
        
        Assert.assertThat(parties.size(), is(1));
        
        
        deletePartyById(1L);
        parties=getParties();
        Assert.assertThat(parties.size(), is(0));
        
    }
    
    
    
    
    
    private void createOrUpdateParty(final PartyEntity party){
        HttpEntity<PartyEntity> entity =  new HttpEntity<PartyEntity>(party, headers);
        ResponseEntity<String> response =
                restTemplate.exchange(URI, HttpMethod.POST, entity, String.class);
        
        Assert.assertThat(response.getStatusCode(), CoreMatchers.is(HttpStatus.CREATED));
    
    }
    
    private List<PartyEntity> getParties(){
        ParameterizedTypeReference<List<PartyEntity>> parties = 
                new ParameterizedTypeReference<List<PartyEntity>>() {};

        
        ResponseEntity<List<PartyEntity>> response =
                restTemplate.exchange(URI, HttpMethod.GET, null, parties);

        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
        return response.getBody();
        
    }
    
    private void deletePartyById(Long id) {
        ResponseEntity<Void> response = 
                restTemplate.exchange(URI  + id, HttpMethod.DELETE, null, Void.class);
        
        Assert.assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));  
        
    }
    
    private PartyEntity findCandidateById(Long id){
        ResponseEntity<PartyEntity> response =
                restTemplate.exchange(URI  + id, HttpMethod.GET, null, PartyEntity.class);
        
        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
        return response.getBody();
    
    }

}
