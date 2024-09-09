package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import vn.edu.iuh.fit.entities.Account;
import vn.edu.iuh.fit.entities.Role;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RoleRepository {

    EntityManager entityManager;

    public RoleRepository() {
        entityManager = Persistence.createEntityManagerFactory("MariaBD").createEntityManager();
    }

    public String getRoleIdByAccountId(String accountId) {
        String query = "SELECT ga.id.roleId FROM GrantAccess ga WHERE ga.id.accountId = :accountId";
        try {
            return entityManager.createQuery(query, String.class)
                    .setParameter("accountId", accountId)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Account, String> getRoleByAccountId(String accountId) {
        Map<Account, String> result = new LinkedHashMap<>();
        String query = "SELECT ga.account, ga.id.roleId FROM GrantAccess ga WHERE ga.id.accountId = :accountId";
        List<Object[]> list = entityManager.createQuery(query, Object[].class)
                .setParameter("accountId", accountId)
                .getResultList();
        for (Object[] objects : list) {
            result.put((Account) objects[0], (String) objects[1]);
        }
        return result;
    }

    public Map<Account, String> gellAccountAndRole() {
        Map<Account, String> result = new LinkedHashMap<>();
        String query = "SELECT ga.account, ga.id.roleId FROM GrantAccess ga";
        List<Object[]> list = entityManager.createQuery(query, Object[].class)
                .getResultList();
        for (Object[] objects : list) {
            result.put((Account) objects[0], (String) objects[1]);
        }
        return result;
    }

    public Map<Account, String> showAllAccountByRole(String role) {
        Map<Account, String> result = new LinkedHashMap<>();
        String query = "SELECT ga.account, ga.id.roleId FROM GrantAccess ga WHERE ga.id.roleId = :role";
        List<Object[]> list = entityManager.createQuery(query, Object[].class)
                .setParameter("role", role)
                .getResultList();
        for (Object[] objects : list) {
            result.put((Account) objects[0], (String) objects[1]);
        }
        return result;
    }

    public Map<Account, String> showAccountByRole(String username, String role) {
        Map<Account, String> result = new LinkedHashMap<>();
        String query = "SELECT ga.account, ga.id.roleId FROM GrantAccess ga WHERE ga.id.accountId = :username AND ga.id.roleId = :role";
        List<Object[]> list = entityManager.createQuery(query, Object[].class)
                .setParameter("username", username)
                .setParameter("role", role)
                .getResultList();
        for (Object[] objects : list) {
            result.put((Account) objects[0], (String) objects[1]);
        }
        return result;
    }
}
