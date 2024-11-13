package iuh.fit.se.backEnd.Repositories;

import iuh.fit.se.backEnd.Models.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface postCommentRepository extends JpaRepository<PostComment, Long> {
}