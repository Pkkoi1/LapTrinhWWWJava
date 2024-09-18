package edu.iuh.fit.backEnd.repositories;

import edu.iuh.fit.backEnd.enums.EmloyeeStatus;
import edu.iuh.fit.backEnd.models.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


public class EmployeeRepository {

    private EntityManager em;
    private EntityTransaction et;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public EmployeeRepository() {
        em = Persistence.createEntityManagerFactory("MariaBD").createEntityManager();
        et = em.getTransaction();
    }
    public void close() {
        em.close();
    }
    public List<Employee> getAll() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class)
        .getResultList();
    }

    public void setStatus(Employee employee, EmloyeeStatus status) {
        employee.setStatus(status);
    }

    public void insertEmployee(Employee employee) {
        try {
            et.begin();
            em.persist(employee);
            et.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            et.rollback();
        }
    }

    public boolean updateEmployee(Employee employee) {
        try {
            et.begin();
            em.merge(employee);
            et.commit();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            et.rollback();
            return false;
        }
    }

    public Optional<Employee> findById(Long id) {
        TypedQuery query = em.createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class);
        query.setParameter("id", id);
        Employee employee = (Employee) query.getSingleResult();
        return employee == null ? Optional.empty() : Optional.of(employee);
    }

    public void updateStatus(Long id, EmloyeeStatus status) {
        TypedQuery<Employee> query = em.createNamedQuery("Employee.findById", Employee.class)
                .setParameter("id", id);
        Employee employee = query.getSingleResult();
        employee.setStatus(status);
        try {
            et.begin();
            em.merge(employee);
            et.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            et.rollback();
        }
    }
}
