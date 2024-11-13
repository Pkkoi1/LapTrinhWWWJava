package iuh.fit.se.frontEnd;

import iuh.fit.se.backEnd.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import iuh.fit.se.backEnd.Services.userService;
import iuh.fit.se.backEnd.Repositories.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private userService userService;

    @Autowired
    private UserRepository userRepository;
    @GetMapping(value = {"/", ""})
    public ModelAndView showListUser() {
        ModelAndView modelAndView = new ModelAndView("Users/list");
        List<User> users = userService.findAll();
        for(User u: users)
        {
            System.out.println(u.getFirstName());
        }
        modelAndView.addObject("users", users);

        return modelAndView;
    }
}
