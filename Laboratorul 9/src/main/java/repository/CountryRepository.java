package repository;

import entity.Country;
import jakarta.persistence.NoResultException;

import java.util.List;

public class CountryRepository extends DataRepository<Country, Integer> {
    @Override
    public List<Country> findByName(String name) {
        try {
            return em.createNamedQuery("Country.findByName")
                    .setParameter("countryName", name)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Country> findAll() {
        return em.createNamedQuery("Country.findAll")
                .getResultList();
    }

    @Override
    public Country findById(Integer id) {
        return em.find(Country.class, id);
    }

    @Override
    public void deleteAll() {
        em.getTransaction().begin();
        int deletedCount = em.createQuery("delete from Country e ")
                .executeUpdate();
        em.getTransaction().commit();
    }
}
