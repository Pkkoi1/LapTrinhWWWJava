package iuh.fit.se.frontEnd;

import iuh.fit.se.backEnd.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import iuh.fit.se.backEnd.Services.userService;
import iuh.fit.se.backEnd.Repositories.UserRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
        modelAndView.addObject("users", users);

        return modelAndView;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        String pass =  userService.maHoa(password);
        User user = userService.findUserByEmailAndPassword(email, pass);
        System.out.println(pass);
        if (user != null) {
            userService.updateLoginTime(email);
            return "redirect:/post";
        }
        return "redirect:/user";
    }

    @GetMapping("/regist")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("Register/register");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }
    @PostMapping("/regist")
    public String register(@ModelAttribute User user) {
        user.setPasswordHash(userService.maHoa(user.getPasswordHash()));
        ZonedDateTime vietnamTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).truncatedTo(ChronoUnit.SECONDS);
        user.setLastLogin(vietnamTime.toInstant());
        user.setRegisteredAt(vietnamTime.toInstant());
        userRepository.save(user);
        return "redirect:/login";
    }
}
