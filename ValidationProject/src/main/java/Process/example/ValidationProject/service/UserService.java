package Process.example.ValidationProject.service;

import Process.example.ValidationProject.model.User;
import Process.example.ValidationProject.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id){
        return userRepository.getById(id);
    }

    public User save(User user) { return userRepository.save(user);
    }

    public void delete(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("user not found"));

        userRepository.delete(user);
    }

    public User getMe(Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }
}