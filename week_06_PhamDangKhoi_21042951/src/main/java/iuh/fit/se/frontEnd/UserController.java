package iuh.fit.se.frontEnd;

import iuh.fit.se.backEnd.Models.User;
import iuh.fit.se.backEnd.Services.postService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import iuh.fit.se.backEnd.Services.userService;
import iuh.fit.se.backEnd.Repositories.UserRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private postService postService;
    @GetMapping(value = {"/", ""})
    public ModelAndView showListUser() {
        ModelAndView modelAndView = new ModelAndView("Users/list");

        List<User> users = userService.findAll();
        modelAndView.addObject("users", users);

        return modelAndView;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
        String pass = userService.maHoa(password);
        if(email.equals("")|| password.equals("")){
            return "redirect:/user";
        }
        User user = userService.findUserByEmailAndPassword(email, pass);

        if (user != null) {
            userService.updateLoginTime(email);
            redirectAttributes.addFlashAttribute("user", user);
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

    @GetMapping("/account/user={id}")
    public ModelAndView account(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView("Users/userAccount");
        modelAndView.addObject("user", userService.findById(id));
        modelAndView.addObject("posts", postService.findPostByAuthor(id));
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView("Users/edit");
        User user = userService.findById(id);
        modelAndView.addObject("user", user);
        return modelAndView;
    }
    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") long id ,@ModelAttribute User user, @RequestParam String currentPassword, @RequestParam String newPassword) {

        String pass = userService.findPasswordById(id);
        if(!currentPassword.equalsIgnoreCase("") && !newPassword.equalsIgnoreCase("") && pass.equals(userService.maHoa(currentPassword))){
            if(!newPassword.equals(currentPassword)){
                user.setPasswordHash(userService.maHoa(newPassword));
                System.out.println("Password match");
            }
            System.out.println("Password not match 1");
        }
        else
        {
            user.setPasswordHash(userService.findPasswordById(id));
        }
        user.setRegisteredAt(userService.findRegisteredAtById(id));
        userRepository.save(user);
        return "redirect:/user/account/user=" + user.getId();
    }
}
