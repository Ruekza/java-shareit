package ru.practicum.gateway.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoOwner {
    private Long id;
    private String name;
    private Long userId;
    private String description;
    private Boolean available;
    private LocalDateTime lastBooking;
    private LocalDateTime nextBooking;
    private List<CommentDto> comments;
}
