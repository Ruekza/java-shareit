package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * TODO Sprint add-controllers.
 */
@Data
@EqualsAndHashCode(of = {"id"})
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    @Email(message = "Некорректный формат email")
    @NotBlank(message = "Почта должна быть заполнена")
    private String email;
}
