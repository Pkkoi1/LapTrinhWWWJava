package iuh.fit.edu.backend.repositories;

import iuh.fit.edu.backend.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRespository extends JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c WHERE " +
            "UPPER(c.compName) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "UPPER(c.phone) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "UPPER(c.webUrl) LIKE CONCAT('%', UPPER(?1), '%') OR " +
            "UPPER(c.email) LIKE CONCAT('%', UPPER(?1), '%') ")
    Page<Company> findByKey(String key, Pageable pageable);

    @Query("SELECT c FROM Company c inner join c.jobs j " +
            "where UPPER(c.jobs) LIKE CONCAT('%', UPPER(?1), '%')")
    Page<Company> findByJob(String key, Pageable pageable);

    @Query("select c from Company c inner join c.jobs j inner join j.jobSkills jobSkills " +
            "where jobSkills.skill.skillName = ?1")
    List<Company> findBySkill(String skillName);
}
