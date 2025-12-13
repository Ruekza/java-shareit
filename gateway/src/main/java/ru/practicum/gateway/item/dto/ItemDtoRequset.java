package ru.practicum.gateway.item.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemDtoRequset {
    private Long id;
    private String name;
    private Long userId;
}
