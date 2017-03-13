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

import lt.javainiai.model.PartyResultsEntity;
import lt.javainiai.service.PartyResultsService;
import lt.javainiai.utils.ConstituencyProgress;
import lt.javainiai.utils.DistrictResultSubmitTime;
import lt.javainiai.utils.MultiMandatePartyResults;

@RestController
@RequestMapping("/party-results/")
public class PartyResultsController {

    private PartyResultsService partyResultsService;

    @Autowired
    public PartyResultsController(PartyResultsService partyResultsService) {
        this.partyResultsService = partyResultsService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PartyResultsEntity save(@Valid @RequestBody PartyResultsEntity partyResults) {
        return this.partyResultsService.saveOrUpdate(partyResults);
    }

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<PartyResultsEntity> findAll() {
        return this.partyResultsService.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PartyResultsEntity findById(@Valid @PathVariable("id") Long id) {
        return this.partyResultsService.findById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Valid @PathVariable("id") Long id) {
        this.partyResultsService.deleteById(id);
    }

    @RequestMapping(value = "district/{districtId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<MultiMandatePartyResults> getMultiMandateResultsInDistrict(
            @Valid @PathVariable("districtId") Long districtId) {
        return partyResultsService.getMultiMandateResultsInDistrict(districtId);
    }

    @RequestMapping(value = "constituency/{constituencyId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<MultiMandatePartyResults> getMultiMandateResultsInConstituency(
            @Valid @PathVariable("constituencyId") Long constituencyId) {
        return partyResultsService.getMultiMandateResultsInConstituency(constituencyId);
    }

    @RequestMapping(value = "total-results", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<MultiMandatePartyResults> getMultiMandateTotalResults() {
        return partyResultsService.getMultiMandateTotalResults();
    }

    @RequestMapping(value = "progress", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ConstituencyProgress> getConstituenciesProgressList() {
        return partyResultsService.getConstituenciesProgressList();
    }

    @RequestMapping(value = "districts-results-time/{constituencyId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<DistrictResultSubmitTime> getDistrictsResultsSubmissionTime(
            @Valid @PathVariable("constituencyId") Long constituencyId) {
        return partyResultsService.getDistrictsResultsSubmissionTime(constituencyId);
    }

}
