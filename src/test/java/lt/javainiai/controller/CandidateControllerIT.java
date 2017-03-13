package lt.javainiai.controller;

import static org.hamcrest.CoreMatchers.is;

import java.sql.Date;
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
import lt.javainiai.model.CandidateEntity;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RiBRIS_Application.class })
@DirtiesContext(classMode =  ClassMode.BEFORE_EACH_TEST_METHOD)
public class CandidateControllerIT {
    
    private static final String URI = "/candidates/";

    @Autowired
    private TestRestTemplate restTemplate;
    
    public static HttpHeaders headers = new HttpHeaders();
    
    @BeforeClass 
    public static void onlyOnce() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8)); 
     }
    
    
    @Test
    public void createCandidateAndCheckIfExist(){
        CandidateEntity candidate = new CandidateEntity();
        candidate.setName("Andrius");
        candidate.setSurname("Kubilius");
        candidate.setBirthDate(new Date(1992-12-12));
        
        createOrUpdateCandidate(candidate);
        
        List<CandidateEntity> candidates = getCandidates();
        Assert.assertThat(candidates.size(), is(1));
        
    }
    
    
    @Test
    public void findCandidateById(){
        CandidateEntity candidate1 = new CandidateEntity();
        candidate1.setName("Andrius");
        candidate1.setSurname("Kubilius");
        candidate1.setBirthDate(new Date(1992-12-13));
        
        CandidateEntity candidate2 = new CandidateEntity();
        candidate2.setName("Rolis");
        candidate2.setSurname("Paksis");
        candidate2.setBirthDate(new Date(1945-12-18));
        
        createOrUpdateCandidate(candidate1);
        createOrUpdateCandidate(candidate2);
        
        
        Assert.assertThat((findCandidateById(2L)).getName(), is("Rolis"));
        Assert.assertThat((findCandidateById(1L)).getName(), is("Andrius"));
    
    }
    
    @Test
    public void updateCandidate(){
        CandidateEntity candidate1 = new CandidateEntity();
        candidate1.setName("Andrius");
        candidate1.setSurname("Kubilius");
        candidate1.setBirthDate(new Date(1992-12-13));
        
        createOrUpdateCandidate(candidate1);
        
        candidate1.setId(1L);
        candidate1.setName("Anatolijus");
        candidate1.setSurname("Kibartas");
        
        createOrUpdateCandidate(candidate1);
        
        Assert.assertEquals("Anatolijus", (findCandidateById(1L)).getName());
        Assert.assertEquals("Kibartas", (findCandidateById(1L)).getSurname());
        
        List<CandidateEntity> candidates = getCandidates();
        Assert.assertThat(candidates.size(), is(1));
        
    }
    
    
    @Test
    public void createCandidateAndCheckIfDeleteWorks(){
        CandidateEntity candidate1 = new CandidateEntity();
        candidate1.setName("Andrius");
        candidate1.setSurname("Kubilius");
        candidate1.setBirthDate(new Date(1992-12-13));
        
        createOrUpdateCandidate(candidate1);
        
        List<CandidateEntity> candidates = getCandidates();
        Assert.assertThat(candidates.size(), is(1));
        deleteCandidateById(1L);
        
        candidates = getCandidates();
        Assert.assertThat(candidates.size(), is(0));
            
    }
    
    
    private void createOrUpdateCandidate(final CandidateEntity candidate){
        
        
        HttpEntity<CandidateEntity> entity =  new HttpEntity<CandidateEntity>(candidate, headers);
        ResponseEntity<String> response =
                restTemplate.exchange(URI, HttpMethod.POST, entity, String.class);
        
        Assert.assertThat(response.getStatusCode(), CoreMatchers.is(HttpStatus.CREATED));
     
    }
    
    private List<CandidateEntity> getCandidates(){
        
        ParameterizedTypeReference<List<CandidateEntity>> candidates =
                new ParameterizedTypeReference<List<CandidateEntity>>() {};

        ResponseEntity<List<CandidateEntity>> response = 
                restTemplate.exchange(URI, HttpMethod.GET, null, candidates);

        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
        return response.getBody(); 
      
    }

    private void deleteCandidateById(Long id){
        
        ResponseEntity<Void> response = 
                restTemplate.exchange(URI  + id, HttpMethod.DELETE, null, Void.class);
       
        Assert.assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        
    }
    
    private CandidateEntity findCandidateById(Long id){
        
        ResponseEntity<CandidateEntity> response =
                restTemplate.exchange(URI  + id, HttpMethod.GET, null, CandidateEntity.class);
        
        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
        return response.getBody();
        
    }
}
