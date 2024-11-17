package iuh.fit.se.frontEnd;


import iuh.fit.se.backEnd.Models.Post;
import iuh.fit.se.backEnd.Models.User;
import iuh.fit.se.backEnd.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import iuh.fit.se.backEnd.Services.postService;
import org.springframework.web.servlet.ModelAndView;
import iuh.fit.se.backEnd.Services.userService;
import iuh.fit.se.backEnd.Repositories.postRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private postService postService;

    @Autowired
    private userService userService;

    @Autowired
    private postRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = {"/", ""})
    public ModelAndView showListPost(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView("Posts/list");

        modelAndView.addObject("posts", postService.findAll());
        if (user == null) {
            user =userService.findById(1L);
        }
        modelAndView.addObject("user", user);
        return modelAndView;
    }
    @GetMapping("/details/{id}")
    public ModelAndView showPostDetails(@PathVariable("id") long id, long userId) {
        ModelAndView modelAndView = new ModelAndView("Posts/details");
        Post post = postService.findById(id);
        modelAndView.addObject("post", post);
        modelAndView.addObject("user", userService.findById(userId));
        return modelAndView;
    }

    @GetMapping("/createPost/user = {id}")
    public ModelAndView createPost(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView("Posts/create");
        Post post = new Post();
        User user = userService.findById(id);
//        post.setAuthor(us);
        modelAndView.addObject("user", user);
        modelAndView.addObject("post", post);
        return modelAndView;
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post, @RequestParam Long userId, RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        post.setAuthor(user);
        post.setCreatedAt(Instant.now());
        post.setPublishedAt(Instant.now());
        post.setPublished(true);
        post.setUpdatedAt(Instant.now());
        Post savedPost = postRepository.save(post);
        redirectAttributes.addAttribute("id", user.getId());
        return "redirect:/user/account/user={id}";
    }
}
