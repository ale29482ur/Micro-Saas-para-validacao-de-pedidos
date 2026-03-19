package Process.example.ValidationProject.controller;

import Process.example.ValidationProject.model.User;
import Process.example.ValidationProject.model.UserDto;
import Process.example.ValidationProject.repository.UserRepository;
import Process.example.ValidationProject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping()
    public void deleteUser(Authentication authentication) {
        userService.delete(authentication);
    }

    @PutMapping()
    public ResponseEntity<User> UpdateUser(Authentication authentication, User user) {
        return userService.update(authentication, user);
    }

    @GetMapping("/me")
    public UserDto getMe(Authentication authentication) {
        return userService.getMe(authentication);
    }
}
