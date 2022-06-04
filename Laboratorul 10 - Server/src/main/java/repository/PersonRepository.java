package repository;

import entity.Message;
import entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class PersonRepository {
    private final EntityManager em = PersistenceUtil.getEntityManager();

    public PersonRepository() {
    }

    public void save(Person person) {
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public Person findByName(String name) {
        try {
            return (Person) em.createNamedQuery("Person.findByName")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void sendMessage(Message message) {
        try {
            em.getTransaction().begin();
            em.persist(message);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public List<Message> readMessage(Person person) {
        try {
            return em.createNamedQuery("Message.findByPersonId")
                    .setParameter(1, person.getId())
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
