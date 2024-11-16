package iuh.fit.se.backEnd.Repositories;

import iuh.fit.se.backEnd.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface postRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.author.id = ?1")
    List<Post> findPostByAuthor(Long id);

}
