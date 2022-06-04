package repository;

import entity.Continent;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ContinentRepository extends DataRepository<Continent, Integer> {
    @Override
    public List<Continent> findByName(String name) {
        try {
            return em.createNamedQuery("Continent.findByName")
                    .setParameter("continentName", name)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Continent> findAll() {
        return em.createNamedQuery("Continent.findAll")
                .getResultList();
    }

    @Override
    public Continent findById(Integer id) {
        return em.find(Continent.class, id);
    }

    @Override
    public void deleteAll() {
        em.getTransaction().begin();
        int deletedCount = em.createQuery("delete from Continent e ")
                .executeUpdate();
        em.getTransaction().commit();
    }
}
