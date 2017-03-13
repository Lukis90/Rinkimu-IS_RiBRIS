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
import lt.javainiai.model.ConstituencyEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RiBRIS_Application.class })
@DirtiesContext(classMode =  ClassMode.BEFORE_EACH_TEST_METHOD)
public class ConstituencyControllerIT {
   
    private static final String URI = "/constituencies/";

    @Autowired
    private TestRestTemplate restTemplate;
    
    public static HttpHeaders headers = new HttpHeaders(); 
    
    @BeforeClass 
    public static void onlyOnce() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
     }
    
    
    @Test
    public void createConstituencyAndCheckIfExist(){
        ConstituencyEntity constituency = new ConstituencyEntity();
        constituency.setName("Kauno");
        
        createOrUpdateConstituency(constituency);
        
        List<ConstituencyEntity> constituencies = getConstituencies();
        Assert.assertThat(constituencies.size(), is(1));         
    
    }
    
   
   @Test
   public void findConstituenciesById(){
       ConstituencyEntity constituency1 = new ConstituencyEntity();
       constituency1.setName("Kauno");

       ConstituencyEntity constituency2 = new ConstituencyEntity();
       constituency2.setName("Alytaus");
       
       createOrUpdateConstituency(constituency1);
       createOrUpdateConstituency(constituency2);
      
       Assert.assertThat((findConstituencyById(2L)).getName(), is("Alytaus"));
       Assert.assertThat((findConstituencyById(1L)).getName(), is("Kauno"));
   
   }
   
   @Test
   public void findConstituenciesByName(){
       ConstituencyEntity constituency1 = new ConstituencyEntity();
       constituency1.setName("Kauno");
       
       ConstituencyEntity constituency2 = new ConstituencyEntity();
       constituency2.setName("Alytaus");
       
       createOrUpdateConstituency(constituency1); 
       createOrUpdateConstituency(constituency2);
       
       Assert.assertThat((findConstituencyByName("Alytaus")).getId(), is(2L)); 
       Assert.assertThat((findConstituencyByName("Kauno")).getId(), is(1L));
  
   }
   
   @Test
   public void updateConstituency(){
       ConstituencyEntity constituency = new ConstituencyEntity();
       constituency.setName("Kauno");
       
       createOrUpdateConstituency(constituency);
       
       constituency.setName("Klaipedos");
       constituency.setId(1L);
       createOrUpdateConstituency(constituency);
       
       Assert.assertEquals("Klaipedos", (findConstituencyById(1L)).getName());
       
       List<ConstituencyEntity> constituencies = getConstituencies();
       Assert.assertThat(constituencies.size(), is(1));       
   
   }

     
    
    @Test
    public void createConstituencyAndCheckIfDeleteWorks(){
        ConstituencyEntity constituency = new ConstituencyEntity();
        constituency.setName("Kauno");
        
        createOrUpdateConstituency(constituency);
   
        List<ConstituencyEntity> constituencies = getConstituencies();
        Assert.assertThat(constituencies.size(), is(1));
        deleteConstituencyById(1L);
        
        constituencies = getConstituencies();
        Assert.assertThat(constituencies.size(), is(0));
  
    }
    
    
    private void createOrUpdateConstituency(final ConstituencyEntity constituency){
        HttpEntity<ConstituencyEntity> entity =  
                new HttpEntity<ConstituencyEntity>(constituency, headers);
        ResponseEntity<String> response =
                restTemplate.exchange(URI, HttpMethod.POST, entity, String.class);
        
        Assert.assertThat(response.getStatusCode(), CoreMatchers.is(HttpStatus.CREATED));
   
    }
     
     
    private List<ConstituencyEntity> getConstituencies(){
       ParameterizedTypeReference<List<ConstituencyEntity>> constituencies = 
               new ParameterizedTypeReference<List<ConstituencyEntity>>() {};

       ResponseEntity<List<ConstituencyEntity>> response = 
               restTemplate.exchange(URI, HttpMethod.GET, null, constituencies);

       Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
       return response.getBody();
   
    }
     
    private void deleteConstituencyById(Long id){
       ResponseEntity<Void> response = 
               restTemplate.exchange(URI  + id, HttpMethod.DELETE, null, Void.class);
        
       Assert.assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    
    }
     
    private ConstituencyEntity findConstituencyById(Long id){
       ResponseEntity<ConstituencyEntity> response = 
               restTemplate.exchange(URI  + id, HttpMethod.GET, null, ConstituencyEntity.class);
         
       Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
       return response.getBody();
    
    }
     
    private ConstituencyEntity findConstituencyByName(String name){
       ResponseEntity<ConstituencyEntity> response = 
               restTemplate.exchange(URI + "/by-name/"  + name, HttpMethod.GET, null, ConstituencyEntity.class);
         
       Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
       return response.getBody();
     
    }

}
