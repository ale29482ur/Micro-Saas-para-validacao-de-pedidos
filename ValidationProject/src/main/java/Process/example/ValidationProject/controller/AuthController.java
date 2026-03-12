package Process.example.ValidationProject.controller;

import Process.example.ValidationProject.model.LoginRequest;
import Process.example.ValidationProject.model.User;
import Process.example.ValidationProject.repository.UserRepository;
import Process.example.ValidationProject.service.JwtService;
import Process.example.ValidationProject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()){
            return ResponseEntity.status(401).body("Email Not found");
        }

        User user = optionalUser.get();

        if (!request.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid Passoword");
        }

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(token);
    }
}
