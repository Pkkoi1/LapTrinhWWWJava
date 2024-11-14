package iuh.fit.se.backEnd.Services;

import iuh.fit.se.backEnd.Models.User;
import iuh.fit.se.backEnd.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class userService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByEmailAndPassword(String email, String passwordHash) {
        return userRepository.findUserByEmailAndPassword(email, passwordHash);
    }

    public void updateLoginTime(String email) {
        User user = userRepository.findUserByEmail(email);
        ZonedDateTime vietnamTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).truncatedTo(ChronoUnit.SECONDS);
        user.setLastLogin(vietnamTime.toInstant());
        userRepository.save(user);

    }

    public String maHoa(String password) {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(password);
    }


}
