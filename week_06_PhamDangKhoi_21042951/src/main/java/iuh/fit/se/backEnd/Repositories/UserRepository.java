package iuh.fit.se.backEnd.Repositories;

import iuh.fit.se.backEnd.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = ?1 and u.passwordHash = ?2")
    User findUserByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findUserByEmail(String email);

    @Query("SELECT u.passwordHash FROM User u WHERE u.id = ?1")
    String findPasswordById(Long id);

    @Query("SELECT u.registeredAt FROM User u WHERE u.id = ?1")
    Instant findRegisteredAtById(Long id);
}