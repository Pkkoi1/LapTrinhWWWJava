package edu.iuh.fit.backEnd.repositories;

import edu.iuh.fit.backEnd.models.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CustomerRepository {
    private EntityManager em;
    private EntityTransaction et;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public CustomerRepository() {
        em = Persistence.createEntityManagerFactory("MariaBD").createEntityManager();
        et = em.getTransaction();
    }

    public void insertCustomer(Customer customer) {
        try {
            et.begin();
            em.persist(customer);
            et.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            et.rollback();
        }
    }

    public Optional<Customer> findById(long id) {
        return Optional.ofNullable(em.find(Customer.class, id));
    }

    public boolean updateCustomer(Customer customer) {
        try {
            et.begin();
            em.merge(customer);
            et.commit();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            et.rollback();
            return false;
        }
    }

    public List<Customer> getAllCustomer() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class)
                .getResultList();
    }





}
