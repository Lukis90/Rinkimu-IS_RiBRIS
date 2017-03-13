package lt.javainiai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.javainiai.model.PartyResultsEntity;
import lt.javainiai.repository.PartyResultsRepository;

@Service
public class PartyResultsService {

    @Autowired
    private PartyResultsRepository partyResultsRepository;

    public PartyResultsEntity saveOrUpdate(PartyResultsEntity partyResults) {
        return partyResultsRepository.saveOrUpdate(partyResults);
    }

    public List<PartyResultsEntity> findAll() {
        return this.partyResultsRepository.findAll();
    }

    public PartyResultsEntity findById(Long id) {
        return this.partyResultsRepository.findById(id);
    }

    public void deleteById(Long id) {
        this.partyResultsRepository.deleteById(id);
    }

}
