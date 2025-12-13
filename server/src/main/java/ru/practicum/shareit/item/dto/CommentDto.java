package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;

    @NotBlank(message = "Укажите текст комментария")
    private String text;

    private String authorName;

    private final LocalDateTime created = LocalDateTime.now();

}
