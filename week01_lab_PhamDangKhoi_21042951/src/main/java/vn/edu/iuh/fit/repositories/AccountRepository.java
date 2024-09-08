package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import vn.edu.iuh.fit.entities.Account;

public class AccountRepository {
    private EntityManager entityManager;

    public AccountRepository() {
        entityManager = Persistence.createEntityManagerFactory("MariaBD").createEntityManager();
    }

    public boolean login(String username, String password) {
        String query = "SELECT a FROM Account a WHERE a.accountId = :username AND a.password = :password";
        try {
            Account account = entityManager.createQuery(query, Account.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
            return account != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Account getAccount(String username) {
        String query = "SELECT a FROM Account a WHERE a.accountId = :username";
        try {
            return entityManager.createQuery(query, Account.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addAccount(Account account) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(account);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return false;
    }
}