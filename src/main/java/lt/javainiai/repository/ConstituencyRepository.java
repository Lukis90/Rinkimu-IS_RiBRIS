package lt.javainiai.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lt.javainiai.model.ConstituencyEntity;

@Repository
// @PreAuthorize("hasRole('ROLE_ADMIN')")
public class ConstituencyRepository implements RepositoryInterface<ConstituencyEntity> {

    @Autowired
    private EntityManager em;

    @Transactional
    public ConstituencyEntity saveOrUpdate(ConstituencyEntity constituency) {
        if (constituency.getId() == null) {
            em.persist(constituency);
            return constituency;
        } else {
            ConstituencyEntity merged = em.merge(constituency);
            em.persist(merged);
            return merged;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ConstituencyEntity> findAll() {
        return em.createQuery("SELECT c FROM ConstituencyEntity c").getResultList();
    }

    public ConstituencyEntity findByName(String name) {
        return (ConstituencyEntity) em.createQuery("SELECT c FROM ConstituencyEntity c WHERE c.name LIKE :name")
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public ConstituencyEntity findById(Long id) {
        return em.find(ConstituencyEntity.class, id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        em.remove(findById(id));
    }

}
