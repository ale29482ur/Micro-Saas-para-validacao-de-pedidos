package Process.example.ValidationProject.repository;

import Process.example.ValidationProject.model.Process;
import Process.example.ValidationProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process, Long> {
    Optional<Process> findByIdAndUser(Long processId, User user);
}
