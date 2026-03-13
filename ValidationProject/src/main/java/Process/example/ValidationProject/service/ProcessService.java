package Process.example.ValidationProject.service;

import Process.example.ValidationProject.model.Process;
import Process.example.ValidationProject.model.StatusPedido;
import Process.example.ValidationProject.model.User;
import Process.example.ValidationProject.repository.ProcessRepository;
import Process.example.ValidationProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProcessService {

    private final UserRepository userRepository;
    private final ProcessRepository processRepository;

    public Process createProcess(Authentication authentication, Process process) {

        try {
            if (process.getDescricao() == null || process.getDescricao().isBlank()) {
                throw new IllegalStateException("Description is null or blank");
            }
            if (process.getProduto() == null || process.getProduto().isBlank()) {
                throw new IllegalStateException("Product is null or blank");
            }
            if (process.getModelo() == null || process.getModelo().isBlank()) {
                throw new IllegalStateException("Model is null or blank");
            }
            if (process.getNomeCliente() == null || process.getNomeCliente().isBlank()) {
                throw new IllegalStateException("Client name is null or blank");
            }

            String email = authentication.getName();

            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

            process.setUser(user);

            process.setStatus(StatusPedido.EM_ANALISE_TECNICA);
            process.setUser(user);

            return processRepository.save(process);

        } catch (IllegalStateException e) {
            throw new RuntimeException("Invalid process data: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error create process", e);
        }
    }

    public Process nextProcess(Authentication authentication, Long processId) {

        try {

            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                throw new IllegalStateException("Authentication is null or unauthorized");
            }

            String email = authentication.getName();

            if (email == null || email.isBlank()) {
                throw new IllegalStateException("The token does not have a valid email address.");
            }

            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));

            Process process = processRepository.findByIdAndUser(processId, user).orElseThrow(() -> new RuntimeException("process not found"));

            process.setStatus(process.getStatus().proximoStatus());

            return processRepository.save(process);

        } catch (IllegalStateException e) {
            throw new RuntimeException("Invalid process data: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error next process", e);
        }
    }

    public Process cancelProcess(Authentication authentication, Long processId) {

        try {

            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                throw new IllegalStateException("Authentication is null or unauthorized");
            }

            String email = authentication.getName();

            if (email == null || email.isBlank()) {
                throw new IllegalStateException("The token does not have a valid email address.");
            }

            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));

            Process process = processRepository.findByIdAndUser(processId, user).orElseThrow(() -> new RuntimeException("process not found"));

            process.setStatus(process.getStatus().cancelStatus());

            return processRepository.save(process);

        } catch (IllegalStateException e) {
            throw new RuntimeException("Invalid process data: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error next process", e);
        }
    }

    public void deleteProcess(Authentication authentication, Long processId) {

        try {

            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                throw new IllegalStateException("Authentication is null or unauthorized");
            }

            String email = authentication.getName();

            if (email == null || email.isBlank()) {
                throw new IllegalStateException("The token does not have a valid email address.");
            }

            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));

            Process process = processRepository.findByIdAndUser(processId, user).orElseThrow(() -> new RuntimeException("process not found"));

            processRepository.delete(process);

        } catch (IllegalStateException e) {
            throw new RuntimeException("Invalid process data: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error next process", e);
        }
    }

    public Process ReadProcess(Long processId) {
        return processRepository.findById(processId).orElseThrow(() -> new RuntimeException("process not found"));
    }
}
