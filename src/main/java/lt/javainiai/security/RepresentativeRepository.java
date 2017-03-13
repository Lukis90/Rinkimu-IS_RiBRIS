package lt.javainiai.security;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lt.javainiai.model.PollingDistrictEntity;
import lt.javainiai.repository.RepositoryInterface;

@Repository
// @PreAuthorize("hasRole('ROLE_ADMIN')")
public class RepresentativeRepository implements RepositoryInterface<RepresentativeEntity> {

    @Autowired
    private EntityManager em;

    @Transactional
    public RepresentativeEntity saveOrUpdate(RepresentativeEntity representative) {
        if (representative.getId() == null) {
            em.persist(representative);
            return representative;
        } else {
            RepresentativeEntity merged = em.merge(representative);
            em.persist(merged);
            return merged;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RepresentativeEntity> findAll() {
        return em.createQuery("SELECT r FROM RepresentativeEntity r").getResultList();
    }

    @Override
    public RepresentativeEntity findById(Long id) {
        return em.find(RepresentativeEntity.class, id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        RepresentativeEntity representative = findById(id);
        PollingDistrictEntity district = representative.getPollingDistrict();
        district.setRepresentative(null);

        em.remove(findById(id));
    }

}
