package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * TODO Sprint add-controllers.
 */
@Data
@EqualsAndHashCode(of = {"id"})
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long id;
    @NotBlank(message = "Имя вещи не может быть пустым")
    private String name;
    @NotBlank(message = "Описание вещи не может быть пустым")
    private String description;
    @NotNull(message = "Статус должен принимать одно из двух значений: true или false")
    private Boolean available;
    private Long owner;
}
