package Process.example.ValidationProject.service;

import Process.example.ValidationProject.model.Process;
import Process.example.ValidationProject.model.StatusPedido;
import Process.example.ValidationProject.model.User;
import Process.example.ValidationProject.repository.ProcessRepository;
import Process.example.ValidationProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProcessService {

    private final UserRepository userRepository;
    private final ProcessRepository processRepository;

    public Process createProcess(Authentication authentication, Process process) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        process.setUser(user);

        process.setStatus(StatusPedido.EM_ANALISE_TECNICA);
        process.setUser(user);

        return processRepository.save(process);
    }

    public Process nextProcess(Authentication authentication, Long processId) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Process process = processRepository
                .findByIdAndUser(processId, user)
                .orElseThrow(() -> new RuntimeException("process not found"));

        process.setStatus(process.getStatus().proximoStatus());

        return processRepository.save(process);
    }

    public Process cancelProcess(Authentication authentication,Long processId) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Process process = processRepository
                .findByIdAndUser(processId, user)
                .orElseThrow(() -> new RuntimeException("process not found"));

        process.setStatus(process.getStatus().cancelStatus());

        return processRepository.save(process);
    }

    public void deleteProcess(Authentication authentication, Long processId){

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Process process = processRepository
                .findByIdAndUser(processId, user)
                .orElseThrow(() -> new RuntimeException("process not found"));

        processRepository.delete(process);
    }

    public Process ReadProcess(Long processId) {
        return processRepository.findById(processId)
                .orElseThrow(() -> new RuntimeException("process not found"));
    }
}
