package iuh.fit.se.backEnd.Repositories;

import iuh.fit.se.backEnd.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface postRepository extends JpaRepository<Post, Long> {

}
