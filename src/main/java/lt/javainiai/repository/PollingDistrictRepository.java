package lt.javainiai.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lt.javainiai.model.PollingDistrictEntity;

@Repository
// @PreAuthorize("hasRole('ROLE_ADMIN')")
public class PollingDistrictRepository implements RepositoryInterface<PollingDistrictEntity> {

    @Autowired
    private EntityManager em;

    @Transactional
    public PollingDistrictEntity saveOrUpdate(PollingDistrictEntity pollingDistrict) {
        if (pollingDistrict.getId() == null) {
            em.persist(pollingDistrict);
            return pollingDistrict;
        } else {
            PollingDistrictEntity merged = em.merge(pollingDistrict);
            em.persist(merged);
            return merged;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PollingDistrictEntity> findAll() {
        return em.createQuery("SELECT p FROM PollingDistrictEntity p").getResultList();
    }

    public PollingDistrictEntity postSpoiledBallots(Long districtId, Long single, Long multi) {
        PollingDistrictEntity oldDistrict = findById(districtId);
        PollingDistrictEntity merged;

        oldDistrict.setSpoiledSingleMandateBallots(single);
        oldDistrict.setSpoiledMultiMandateBallots(multi);

        merged = em.merge(oldDistrict);
        em.persist(merged);
        return merged;
    }

    @Override
    public PollingDistrictEntity findById(Long id) {
        return em.find(PollingDistrictEntity.class, id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        em.remove(findById(id));
    }

}