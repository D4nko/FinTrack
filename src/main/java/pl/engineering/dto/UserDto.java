package pl.engineering.dto;

import pl.engineering.model.User;

public record UserDto(int id, String name, String surname, Integer age) {

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getAge());
    }
}
