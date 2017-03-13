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

import lt.javainiai.security.RepresentativeEntity;
import lt.javainiai.service.RepresentativeService;

@RestController
@RequestMapping("/representatives/")
public class RepresentativeController {

    private RepresentativeService representativeService;

    @Autowired
    public RepresentativeController(RepresentativeService representativeService) {
        this.representativeService = representativeService;
    }

    // Register or update
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentativeEntity saveOrUpdate(@Valid @RequestBody RepresentativeEntity representative) {
        return this.representativeService.saveOrUpdate(representative);
    }

    // Find all
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<RepresentativeEntity> findAll() {
        return this.representativeService.findAll();
    }

    // Find one
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public RepresentativeEntity findById(@Valid @PathVariable("id") Long id) {
        return this.representativeService.findById(id);
    }

    // Delete one
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Valid @PathVariable("id") Long id) {
        this.representativeService.deleteById(id);
    }

}
