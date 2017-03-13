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

import lt.javainiai.model.CandidatesResultsRatingEntity;
import lt.javainiai.service.CandidatesResultsRatingService;

@RestController
@RequestMapping("/candidates-results/rating")
public class CandidatesResultsRatingController {

    private CandidatesResultsRatingService candidatesResultsService;
    
    @Autowired
    public CandidatesResultsRatingController(CandidatesResultsRatingService candidatesResultsService){
        this.candidatesResultsService = candidatesResultsService;
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CandidatesResultsRatingEntity save(@Valid @RequestBody CandidatesResultsRatingEntity candidatesResults){
        return candidatesResultsService.saveOrUpdate(candidatesResults);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CandidatesResultsRatingEntity> findAll() {
        return candidatesResultsService.findAll();
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CandidatesResultsRatingEntity findById(@Valid @PathVariable("id") Long id){
        return candidatesResultsService.findById(id);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Valid @PathVariable("id") Long id){
        this.candidatesResultsService.deleteById(id);
    }
    
}
