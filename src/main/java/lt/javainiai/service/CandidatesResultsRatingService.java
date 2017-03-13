package lt.javainiai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.javainiai.model.CandidatesResultsRatingEntity;
import lt.javainiai.repository.CandidatesResultsRatingRepository;

@Service
public class CandidatesResultsRatingService {
    
    @Autowired
    private CandidatesResultsRatingRepository candidatesResultsRepository;

    public CandidatesResultsRatingEntity saveOrUpdate(CandidatesResultsRatingEntity candidatesResults){
        return this.candidatesResultsRepository.saveOrUpdate(candidatesResults);
    }
    
    public List<CandidatesResultsRatingEntity> findAll() {
        return this.candidatesResultsRepository.findAll();
    }
    
    public CandidatesResultsRatingEntity findById(Long id){
        return this.candidatesResultsRepository.findById(id);
    }
    
    public void deleteById(Long id) {
        this.candidatesResultsRepository.deleteById(id);
    }
}
