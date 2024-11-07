package iuh.fit.edu.backend.repositories;

import iuh.fit.edu.backend.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
