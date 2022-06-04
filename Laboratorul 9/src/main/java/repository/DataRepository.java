package repository;

import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class DataRepository
        <T, ID extends Serializable> {
    protected EntityManager em = PersistenceUtil.getEntityManager(); //create it somehow

    public abstract List<T> findByName(String name);

    public void delete(T entity) {
        long startTime = System.nanoTime();

        try {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e);
        }

        long endTime = System.nanoTime();

        System.out.println("delete execution time: " + (endTime - startTime) + " nanoseconds");
    }

    public abstract void deleteAll();

    public void deleteAll(Iterable<T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    public void deleteAllById(Iterable<ID> ids) {
        for (ID id : ids) {
            deleteById(id);
        }
    }

    public void deleteById(ID id) {
        em.getTransaction().begin();
        em.remove(findById(id));
        em.getTransaction().commit();
    }

    public boolean existsById(ID id) {
        return findById(id) != null;
    }

    public abstract List<T> findAll();

    public Iterable<T> findAllById(Iterable<ID> ids) {
        Set<T> tSet = new HashSet<>();

        for (ID id : ids) {
            tSet.add(findById(id));
        }

        return tSet;
    }

    public abstract T findById(ID id);

    public void save(T entity) {
        long startTime = System.nanoTime();

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e);
        }

        long endTime = System.nanoTime();

        System.out.println("persist execution time: " + (endTime - startTime) + " nanoseconds");
    }

    public void saveAll(Iterable<T> entities) {
        for (T entity : entities) {
            save(entity);
        }
    }
}
