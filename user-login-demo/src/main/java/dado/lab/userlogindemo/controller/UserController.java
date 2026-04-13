package dado.lab.userlogindemo.controller;

import dado.lab.userlogindemo.entity.User;
import dado.lab.userlogindemo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.SaslServer;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (userRepository.findByUsername(username) != null) {
            return Map.of("message", "username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userRepository.save(user);

        return Map.of("message", "User: " + username + " successfully registered !");

    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (userRepository.findByUsername(username) == null) {
            return Map.of("message", "User not exists!");
        } else if (!userRepository.findByUsername(username).getPassword().equals(password)) {
            return Map.of("message", "Wrong password!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userRepository.save(user);

        return Map.of("message", "User: " + username + " successfully registered!");
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
      return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
      return userRepository.findAll();
    }

    @PutMapping("/users/{id}")
    public Map<String, String> changePassword(
            @RequestBody Map<String, String> body,
            @PathVariable Long id) {
        String username = body.get("username");
        String curPassword = body.get("current_password");
        String newPassword = body.get("new_password");

        User user = userRepository.findById(id).orElse(null);

        if (user == null || !user.getPassword().equals(curPassword)) {
            return (user == null) ?
                    Map.of("message", "User not exist!") :
                    Map.of("message", "Wrong current password!");
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        return Map.of("message", "Password changed successfully!");
    }

    @DeleteMapping("/users/{id}")
    public Map<String, String> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return Map.of("message", "User not exist!");
        }

        userRepository.delete(user);

        return Map.of("message", "User: " + user.getUsername() + " deleted successfully!");
    }
}
