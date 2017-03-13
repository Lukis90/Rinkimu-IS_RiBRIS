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
import lt.javainiai.security.RepresentativeEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RiBRIS_Application.class })
@DirtiesContext(classMode =  ClassMode.BEFORE_EACH_TEST_METHOD)
public class RepresentativeControllerIT {

    private static final String URI = "/representatives/";

    @Autowired
    private TestRestTemplate restTemplate;
    
    public static HttpHeaders headers = new HttpHeaders();
    
    @BeforeClass 
    public static void onlyOnce() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8)); 
     }
    
    
    @Test
    public void createRepresentativeAndCheckIfExist(){
        RepresentativeEntity representative = new RepresentativeEntity();
        representative.setName("Jonas");
        representative.setSurname("Jonaitis");
        
        createOrUpdate(representative);
        
        List<RepresentativeEntity> representatives = getRepresentatives();
        Assert.assertThat(representatives.size(), is(1));
        
    }
    
    @Test
    public void findRepresentativesById(){
        RepresentativeEntity representative1 = new RepresentativeEntity();
        representative1.setName("Jonas");
        representative1.setSurname("Jonaitis");
        
        RepresentativeEntity representative2 = new RepresentativeEntity();
        representative2.setName("Povilas");
        representative2.setSurname("Pusis");
        
        createOrUpdate(representative1);
        createOrUpdate(representative2);
        
        Assert.assertThat((findRepresentativeById(2L)).getName(), is("Povilas"));
        Assert.assertThat((findRepresentativeById(1L)).getName(), is("Jonas"));
        
    }
    
    @Test
    public void updateRepresentative(){
        RepresentativeEntity representative1 = new RepresentativeEntity();
        representative1.setName("Jonas");
        representative1.setSurname("Jonaitis");
        
        createOrUpdate(representative1);
        
        representative1.setName("Karolis");
        representative1.setId(1L);
        createOrUpdate(representative1);
        
        Assert.assertEquals("Karolis", (findRepresentativeById(1L)).getName());
        
        List<RepresentativeEntity> representatives = getRepresentatives();
        Assert.assertThat(representatives.size(), is(1));   
        
    }
    
    @Test
    public void createRepresentativeAndCheckIfDeleteWorks(){
        RepresentativeEntity representative1 = new RepresentativeEntity();
        representative1.setName("Jonas");
        representative1.setSurname("Jonaitis");
        
        createOrUpdate(representative1);
   
        List<RepresentativeEntity> representatives = getRepresentatives();
        Assert.assertThat(representatives.size(), is(1));
        deleteRepresentativeById(1L);
        
        representatives = getRepresentatives();
        Assert.assertThat(representatives.size(), is(0));
  
    }
    
    
    
    
    private void createOrUpdate(final RepresentativeEntity representative){
        HttpEntity<RepresentativeEntity> entity =  
                new HttpEntity<RepresentativeEntity>(representative, headers);
        ResponseEntity<String> response =
                restTemplate.exchange(URI, HttpMethod.POST, entity, String.class);
        
        Assert.assertThat(response.getStatusCode(), CoreMatchers.is(HttpStatus.CREATED));
    
    }
    
    private List<RepresentativeEntity> getRepresentatives(){
        ParameterizedTypeReference<List<RepresentativeEntity>> representatives = 
                new ParameterizedTypeReference<List<RepresentativeEntity>>() {};

        ResponseEntity<List<RepresentativeEntity>> response = 
                restTemplate.exchange(URI, HttpMethod.GET, null, representatives);

        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
        return response.getBody();
    
    }
    
    private void deleteRepresentativeById(Long id){
        ResponseEntity<Void> response = 
                restTemplate.exchange(URI  + id, HttpMethod.DELETE, null, Void.class);
         
        Assert.assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }
    
    private RepresentativeEntity findRepresentativeById(Long id){
        ResponseEntity<RepresentativeEntity> response = 
                restTemplate.exchange(URI  + id, HttpMethod.GET, null, RepresentativeEntity.class);
          
        Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
        return response.getBody();
    }
    
    
}
