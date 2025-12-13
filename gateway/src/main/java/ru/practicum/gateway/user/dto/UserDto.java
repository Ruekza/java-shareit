package ru.practicum.gateway.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    private Long id;
    private String name;
    @Email(message = "Некорректный формат email")
    @NotBlank(message = "Почта должна быть заполнена")
    private String email;
}
