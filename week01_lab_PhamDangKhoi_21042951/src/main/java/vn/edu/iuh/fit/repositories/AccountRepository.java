package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Persistence;
import vn.edu.iuh.fit.entities.Account;
import vn.edu.iuh.fit.entities.GrantAccess;
import vn.edu.iuh.fit.entities.GrantAccessId;

import java.util.List;

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

    public List<Account> getListAccount(String username) {
        String query = "SELECT a FROM Account a WHERE a.accountId = :username";
        try {
            return entityManager.createQuery(query, Account.class)
                    .setParameter("username", username)
                    .getResultList();
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
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAllAccount() {
        return entityManager.createNamedQuery("getAllAccount", Account.class).getResultList();

    }

    public boolean deleteAccount(String username) {
        try {
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("Account.deleteByAccountId")
                    .setParameter("accountId", username)
                    .executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertGrantAccess(String username, String role) {
        GrantAccessId grantAccessId = new GrantAccessId();
        grantAccessId.setAccountId(username);
        grantAccessId.setRoleId(role);
        GrantAccess grantAccess = new GrantAccess();
        grantAccess.setId(grantAccessId);
        grantAccess.setIsGrant(true);
        grantAccess.setAccount(getAccount(username));

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(grantAccess);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccount(String username, String password, String fullName, String email, String phone, Byte status) {
        try {
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("Account.updateByAccountId")
                    .setParameter("accountId", username)
                    .setParameter("fullName", fullName)
                    .setParameter("password", password)
                    .setParameter("email", email)
                    .setParameter("phone", phone)
                    .setParameter("status", status)
                    .executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteGrantAccess(String username, String role) {
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery("DELETE FROM GrantAccess ga WHERE ga.id.accountId = :username AND ga.id.roleId = :role")
                    .setParameter("username", username)
                    .setParameter("role", role)
                    .executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}