package ru.practicum.gateway.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;
import ru.practicum.gateway.user.dto.UserDto;


import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@ToString
public class ItemRequestDto {
    private Long id;

    @NotBlank(message = "Описание запроса не может быть пустым")
    private String description;

    private UserDto requestor;

    private LocalDateTime created;
}
