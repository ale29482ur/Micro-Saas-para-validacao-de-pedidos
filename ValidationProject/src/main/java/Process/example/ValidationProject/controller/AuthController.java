package Process.example.ValidationProject.controller;

import Process.example.ValidationProject.model.LoginRequest;
import Process.example.ValidationProject.model.User;
import Process.example.ValidationProject.repository.UserRepository;
import Process.example.ValidationProject.service.AuthService;
import Process.example.ValidationProject.service.JwtService;
import Process.example.ValidationProject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }


}
