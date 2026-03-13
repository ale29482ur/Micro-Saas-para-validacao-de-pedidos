package Process.example.ValidationProject.Mapper;

import Process.example.ValidationProject.model.User;
import Process.example.ValidationProject.model.UserDto;

public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setStartDate(user.getStartDate());
        dto.setProcess(user.getProcess());
        return dto;
    }
}
