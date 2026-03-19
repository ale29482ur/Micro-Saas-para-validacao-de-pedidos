package Process.example.ValidationProject.controller;

import Process.example.ValidationProject.model.Process;
import Process.example.ValidationProject.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/process")
@CrossOrigin(origins = "*")
public class ProcessController {

    private final ProcessService processService;

    @PostMapping()
    public ResponseEntity<Process> create(Authentication authentication, @RequestBody Process process) {
        return ResponseEntity.ok(processService.createProcess(authentication, process));
    }

    @GetMapping("/{processId}")
    public Process read(@PathVariable Long processId) {
        return processService.ReadProcess(processId);
    }

    @PutMapping("/{processId}")
    public ResponseEntity<Process> Update(@PathVariable Long processId, Authentication authentication, Process processUpdate) {
        return processService.updateProcess(processId, authentication, processUpdate);
    }

    @PutMapping("/{processId}/next")
    public ResponseEntity<Process> next(Authentication authentication, @PathVariable Long processId) {
        return ResponseEntity.ok(processService.nextProcess(authentication, processId));
    }

    @PutMapping("/{processId}/cancel")
    public ResponseEntity<Process> cancel(Authentication authentication, @PathVariable Long processId) {
        return ResponseEntity.ok(processService.cancelProcess(authentication, processId));
    }

    @DeleteMapping("/{processId}/delete")
    public void delete(Authentication authentication, @PathVariable Long processId) {
        processService.deleteProcess(authentication, processId);
    }
}
