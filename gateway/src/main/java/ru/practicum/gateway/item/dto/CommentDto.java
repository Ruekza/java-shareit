package ru.practicum.gateway.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class CommentDto {
    private Long id;

    @NotBlank(message = "Укажите текст комментария")
    private String text;

    private String authorName;

    private final LocalDateTime created = LocalDateTime.now();

}
