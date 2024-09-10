package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.Date;

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
}
