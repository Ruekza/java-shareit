package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.shareit.item.dto.ItemDtoRequset;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestWithAnswerDto {
    private Long id;

    private String description;

    private LocalDateTime created;

    private List<ItemDtoRequset> items;
}
