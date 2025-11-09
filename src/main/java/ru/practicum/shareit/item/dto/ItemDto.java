package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

/**
 * TODO Sprint add-controllers.
 */
@Data
@ToString
public class ItemDto {
    private Long id;
    @NotBlank(message = "Имя вещи не может быть пустым")
    private String name;
    @NotBlank(message = "Описание вещи не может быть пустым")
    private String description;
    @NotNull(message = "Статус должен принимать одно из двух значений: true или false")
    private Boolean available;
}
