package iuh.fit.se.frontEnd;


import iuh.fit.se.backEnd.Models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import iuh.fit.se.backEnd.Services.postService;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private postService postService;

    @GetMapping(value = {"/", ""})
    public ModelAndView showListPost() {
        ModelAndView modelAndView = new ModelAndView("Posts/list");

        modelAndView.addObject("posts", postService.findAll());
        return modelAndView;
    }
    @GetMapping("/details/{id}")
    public ModelAndView showPostDetails(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView("Posts/details");
        Post post = postService.findById(id);
        modelAndView.addObject("post", post);
        return modelAndView;
    }

}
