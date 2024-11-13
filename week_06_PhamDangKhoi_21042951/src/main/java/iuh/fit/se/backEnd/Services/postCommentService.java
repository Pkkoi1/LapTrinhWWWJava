package iuh.fit.se.backEnd.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import iuh.fit.se.backEnd.Repositories.postCommentRepository;

@Service
public class postCommentService {
    @Autowired
    private postCommentRepository postCommentRepository;
}
