package fem.myrestfulservice.dao;

import fem.myrestfulservice.bean.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static Long userCount = 3L;

    static {
        users.add(new User(1L, "kenneth", LocalDateTime.now()));
        users.add(new User(2L, "Alice", LocalDateTime.now()));
        users.add(new User(3L, "Elena", LocalDateTime.now()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }

        if (user.getJoinDate() == null) {
            user.setJoinDate(LocalDateTime.now());
        }

        users.add(user);
        return user;
    }

    public User findOne(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }
}