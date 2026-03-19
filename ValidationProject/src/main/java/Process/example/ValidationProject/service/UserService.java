package Process.example.ValidationProject.service;

import Process.example.ValidationProject.Mapper.UserMapper;
import Process.example.ValidationProject.model.User;
import Process.example.ValidationProject.model.UserDto;
import Process.example.ValidationProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    public User save(User user) {

        try {

            if (user.getName() == null || user.getName().isBlank() || user.getName().length() < 3 || user.getName().length() > 50) {
                throw new IllegalStateException("The name must be between 3 and 50 characters long.");
            }
            if (user.getAge() == null || user.getAge() < 18 || user.getAge() > 100) {
                throw new IllegalStateException("The age must be greater than 18 and less than 100.");
            }
            if (user.getEmail() == null || user.getEmail().isBlank() || user.getEmail().length() < 5) {
                throw new IllegalStateException("The email address must be longer than 5 characters and be valid.");
            }
            if (user.getPassword() == null || user.getPassword().isBlank() || user.getPassword().length() < 3) {
                throw new IllegalStateException("The password must be longer than 3 characters.");
            }

            Optional<User> userBank = userRepository.findByEmail(user.getEmail());

            if (userBank.isPresent()) {
                throw new IllegalStateException("The email is already registered.");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            return userRepository.save(user);

        } catch (IllegalStateException e) {
            throw new RuntimeException("Invalid user data: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    public void delete(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Unauthorized");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));

        userRepository.delete(user);
    }

    public ResponseEntity<User> update(Authentication authentication, User userUpdate) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        if (userUpdate.getName() == null && userUpdate.getAge() == null && userUpdate.getEmail() == null && userUpdate.getPassword() == null) {
            throw new IllegalStateException("User cant be null");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));

        user.setEmail(userUpdate.getEmail());
        user.setAge(userUpdate.getAge());
        user.setPassword(userUpdate.getPassword());
        user.setName(userUpdate.getName());

        return ResponseEntity.ok(userRepository.save(user));
    }

    public UserDto getMe(Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("Unauthorized");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toDto(user);
    }
}