package lt.javainiai.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lt.javainiai.model.CandidatesResultsSingleMandateEntity;

@Repository
public class CandidatesResultsSingleMandateRepository implements 
				RepositoryInterface<CandidatesResultsSingleMandateEntity>{

    @Autowired
    private EntityManager em;

    @Override
    @Transactional
    public CandidatesResultsSingleMandateEntity saveOrUpdate(CandidatesResultsSingleMandateEntity candidatesResults) {
        if (candidatesResults.getId() == null) {
            em.persist(candidatesResults);
            return candidatesResults;
        } else {
        	CandidatesResultsSingleMandateEntity merged = em.merge(candidatesResults);
            return merged;
        }
    }

	@SuppressWarnings("unchecked")
	@Override
    public List<CandidatesResultsSingleMandateEntity> findAll() {
        return em.createQuery("SELECT c FROM CandidatesResultsSingleMandateEntity c").getResultList();
    }

    @Override
    public CandidatesResultsSingleMandateEntity findById(Long id) {
        return em.find(CandidatesResultsSingleMandateEntity.class, id);
    }

    @Override
    public void deleteById(Long id) {
    	CandidatesResultsSingleMandateEntity candidateResultsToRemove = 
		em.find(CandidatesResultsSingleMandateEntity.class, id);
			em.remove(candidateResultsToRemove);
    }
}
