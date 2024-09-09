package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.Date;

public class LogRepository {
    EntityManager entityManager;

    public LogRepository() {
        entityManager = Persistence.createEntityManagerFactory("MariaBD").createEntityManager();
    }

    public void addLog(String username, Date logInTime, Date logOutTime) {
        try {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("INSERT INTO Log (account_id, login_time, logout_time) VALUES (?, ?, ?)")
                    .setParameter(1, username)
                    .setParameter(2, logInTime)
                    .setParameter(3, logOutTime)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
