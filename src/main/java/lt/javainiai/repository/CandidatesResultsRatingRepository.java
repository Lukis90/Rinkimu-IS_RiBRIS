package lt.javainiai.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lt.javainiai.model.CandidatesResultsRatingEntity;

@Repository
// @PreAuthorize("hasRole('ROLE_REPRESENTATIVE')")
public class CandidatesResultsRatingRepository implements RepositoryInterface<CandidatesResultsRatingEntity> {

    @Autowired
    private EntityManager em;

    @Override
    @Transactional
    public CandidatesResultsRatingEntity saveOrUpdate(CandidatesResultsRatingEntity inputResult) {
        if (inputResult.getId() == null) {
            em.persist(inputResult);
            return inputResult;
        } else {
            CandidatesResultsRatingEntity merged = em.merge(inputResult);
            em.persist(merged);
            return merged;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CandidatesResultsRatingEntity> findAll() {
        return em.createQuery("SELECT c FROM CandidatesResultsRatingEntity c").getResultList();
    }

    @Override
    public CandidatesResultsRatingEntity findById(Long id) {
        return em.find(CandidatesResultsRatingEntity.class, id);
    }

    @Override
    public void deleteById(Long id) {
        em.remove(findById(id));
    }

}
