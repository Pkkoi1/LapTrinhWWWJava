package iuh.fit.edu.backend.repositories;

import iuh.fit.edu.backend.models.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("SELECT c FROM Candidate c inner join c.candidateSkills sk WHERE " +
            "UPPER(sk.skill.skillName) LIKE CONCAT('%', UPPER(?1), '%')" +
            "ORDER BY sk.skillLevel")
    Page<Candidate> findBySkill(String skillName, Pageable pageable);

    @Query("SELECT c FROM Candidate c  WHERE " +
            "UPPER(c.fullName) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "UPPER(c.phone) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "UPPER(c.email) LIKE CONCAT('%', UPPER(?1), '%') ")
    Page<Candidate> findByKey(String ket, Pageable pageable);

    @Query("SELECT c FROM Candidate c JOIN c.candidateSkills cs JOIN JobSkill js ON cs.skill.id = js.skill.id " +
            "WHERE js.job.id = :jobId AND cs.skillLevel >= js.skillLevel " +
            "GROUP BY c.id " +
            "HAVING COUNT(cs.id) > 0 " +
            "ORDER BY COUNT(cs.id) DESC")
    Page<Candidate> findMatchingCandidates(Long jobId, Pageable pageable);
}
