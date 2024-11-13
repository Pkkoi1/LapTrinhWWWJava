package iuh.fit.se.backEnd.Services;

import iuh.fit.se.backEnd.Models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import iuh.fit.se.backEnd.Repositories.postRepository;

import java.util.List;

@Service
public class postService {

    @Autowired
    private postRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
