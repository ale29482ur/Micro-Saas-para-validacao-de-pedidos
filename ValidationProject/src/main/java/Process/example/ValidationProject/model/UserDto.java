package Process.example.ValidationProject.model;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {

    private String name;
    private Integer age;
    private String email;
    private LocalDateTime startDate;
    private List<Process> process = new ArrayList<>();
}


