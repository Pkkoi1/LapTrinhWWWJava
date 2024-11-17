package iuh.fit.se.frontEnd;

import iuh.fit.se.backEnd.Services.postService;
import iuh.fit.se.backEnd.Services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/author")

public class AuthorController {
    @Autowired
    private userService userService;
    @Autowired
    private postService postService;


    @GetMapping("/accountDetail")
    public ModelAndView showDetailsAuthor(long id, long userId) {
        ModelAndView mav = new ModelAndView("Authors/details");
        mav.addObject("author", userService.findById(id));
        mav.addObject("user", userService.findById(userId));
        mav.addObject("posts", postService.findPostByAuthor(id));

        return mav;
    }
}
