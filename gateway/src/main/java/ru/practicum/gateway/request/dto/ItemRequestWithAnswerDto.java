package ru.practicum.gateway.request.dto;

import lombok.Data;
import lombok.ToString;
import ru.practicum.gateway.item.dto.ItemDtoRequset;


import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class ItemRequestWithAnswerDto {
    private Long id;

    private String description;

    private LocalDateTime created;

    private List<ItemDtoRequset> items;
}
