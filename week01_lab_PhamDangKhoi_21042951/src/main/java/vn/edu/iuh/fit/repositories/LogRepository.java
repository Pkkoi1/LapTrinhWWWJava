package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import vn.edu.iuh.fit.entities.Log;

import java.util.Date;
import java.util.List;

public class LogRepository {
    EntityManager entityManager;

    public LogRepository() {
        entityManager = Persistence.createEntityManagerFactory("MariaBD").createEntityManager();
    }

    public void addLog(String username, Date logInTime, Date logOutTime, String note) {
        try {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("INSERT INTO Log (account_id, login_time, logout_time,notes) VALUES (?, ?, ?,?)")
                    .setParameter(1, username)
                    .setParameter(2, logInTime)
                    .setParameter(3, logOutTime)
                    .setParameter(4, note)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Log> findLogbyAccount(String accountID) {
        String query = "SELECT l FROM Log l WHERE l.accountId = :accountID";
        try {
            return entityManager.createQuery(query, Log.class)
                    .setParameter("accountID", accountID)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
