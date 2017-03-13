package lt.javainiai.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lt.javainiai.model.PartyResultsEntity;

@Repository
// @PreAuthorize("hasRole('ROLE_REPRESENTATIVE')")
public class PartyResultsRepository implements RepositoryInterface<PartyResultsEntity> {

    @Autowired
    private EntityManager em;

    @Override
    @Transactional
    public PartyResultsEntity saveOrUpdate(PartyResultsEntity partyResults) {
        if (partyResults.getId() == null) {
            em.persist(partyResults);
            return partyResults;
        } else {
            PartyResultsEntity merged = em.merge(partyResults);
            return merged;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PartyResultsEntity> findAll() {
        return em.createQuery("SELECT  c FROM PartyResultsEntity c").getResultList();
    }

    @Override
    public PartyResultsEntity findById(Long id) {
        return em.find(PartyResultsEntity.class, id);
    }

    @Override
    public void deleteById(Long id) {
        em.remove(findById(id));
    }

}
