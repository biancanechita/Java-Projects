package repository;

import entity.City;
import entity.Country;
import jakarta.persistence.NoResultException;

import java.util.List;

public class CityRepository extends DataRepository<City, Integer> {
    public List<City> findByCountry(Country country) {
        return em.createNamedQuery("City.findByCountry")
                .setParameter(1, country)
                .getResultList();
    }

    @Override
    public List<City> findByName(String name) {
        try {
            return em.createNamedQuery("City.findByName")
                    .setParameter("cityName", name)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<City> findAll() {
        return em.createNamedQuery("City.findAll")
                .getResultList();
    }

    @Override
    public City findById(Integer id) {
        return em.find(City.class, id);
    }

    @Override
    public void deleteAll() {
        em.getTransaction().begin();
        int deletedCount = em.createQuery("delete from City e")
                .executeUpdate();
        em.getTransaction().commit();
    }
}
