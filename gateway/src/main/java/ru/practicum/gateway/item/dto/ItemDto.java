package ru.practicum.gateway.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    @NotBlank(message = "Имя вещи не может быть пустым")
    private String name;
    private Long userId;
    @NotBlank(message = "Описание вещи не может быть пустым")
    private String description;
    @NotNull(message = "Статус должен принимать одно из двух значений: true или false")
    private Boolean available;
    private List<CommentDto> comments;
    @JsonProperty("requestId")
    @Nullable
    private Long request;
}
