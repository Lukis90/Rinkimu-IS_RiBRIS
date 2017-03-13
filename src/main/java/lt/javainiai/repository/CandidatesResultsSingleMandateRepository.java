package lt.javainiai.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lt.javainiai.model.CandidatesResultsSingleMandateEntity;

@Repository
// @PreAuthorize("hasRole('ROLE_REPRESENTATIVE')")
public class CandidatesResultsSingleMandateRepository
        implements RepositoryInterface<CandidatesResultsSingleMandateEntity> {

    @Autowired
    private EntityManager em;

    @Override
    @Transactional
    public CandidatesResultsSingleMandateEntity saveOrUpdate(CandidatesResultsSingleMandateEntity inputResult) {
        if (inputResult.getId() == null) {
            em.persist(inputResult);
            return inputResult;
        } else {
            CandidatesResultsSingleMandateEntity merged = em.merge(inputResult);
            em.persist(merged);
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
        em.remove(findById(id));
    }

}
