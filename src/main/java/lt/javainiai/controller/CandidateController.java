package lt.javainiai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lt.javainiai.model.CandidateEntity;
import lt.javainiai.service.CandidateService;

@RestController
@RequestMapping("/candidates/")
public class CandidateController {

    private CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    // Register or update
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CandidateEntity saveOrUpdate(@Valid @RequestBody CandidateEntity candidate) {
        return candidateService.saveOrUpdate(candidate);
    }

    // Find all
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateEntity> findAll() {
        return candidateService.findAll();
    }
    
    // Find all by constituency
    @RequestMapping(value = "/by-constituency/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateEntity> findByConstituencyId(@Valid @PathVariable("id") Long id) {
        return candidateService.findAllFromConstituency(id);
    }
    
    // Find all by constituency
    @RequestMapping(value = "/by-party/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateEntity> findByPartyId(@Valid @PathVariable("id") Long id) {
        return candidateService.findAllFromParty(id);
    }

    // Find one
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CandidateEntity findById(@Valid @PathVariable("id") Long id) {
        return candidateService.findById(id);
    }

    // Delete one
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Valid @PathVariable("id") Long id) {
        candidateService.deleteById(id);
    }
    
    @RequestMapping(value = "/by-constituency/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByConstituencyId(@Valid @PathVariable("id") Long id){
    	candidateService.deleteByConstituencyId(id);
    }
    
    @RequestMapping(value = "/by-party/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByPartyId(@Valid @PathVariable("id") Long id){
    	candidateService.deleteByPartyId(id);
    }
}
