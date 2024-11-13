package iuh.fit.edu.backend.repositories;

import iuh.fit.edu.backend.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import iuh.fit.edu.backend.enums.skillLevel;


import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j join j.company c where c.id = ?1")
    Page<Job> findCompanyJobs(Long key, org.springframework.data.domain.Pageable pageable);

    @Query(" select j from Job j inner join j.jobSkills jobSkills " +
            "where jobSkills.skillLevel >= ?1 and jobSkills.skill.skillName = ?2")
    List<Job> findJobsBySkillLevelAndSkillName(skillLevel skillLevel, String skillName);

}
