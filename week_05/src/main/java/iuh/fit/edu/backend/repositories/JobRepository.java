package iuh.fit.edu.backend.repositories;

import iuh.fit.edu.backend.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j join j.company c where c.id = ?1")
    Page<Job> findCompanyJobs(Long key, org.springframework.data.domain.Pageable pageable);

    @Query("SELECT j FROM Job j JOIN j.jobSkills js JOIN CandidateSkill cs ON js.skill.id = cs.skill.id " +
            "WHERE cs.can.id = :candidateId AND cs.skillLevel >= js.skillLevel " +
            "GROUP BY j.id " +
            "HAVING COUNT(js.id) > 0 " +
            "ORDER BY COUNT(js.id) DESC")
    Page<Job> findMatchingJobs(Long candidateId, Pageable pageable);

}
