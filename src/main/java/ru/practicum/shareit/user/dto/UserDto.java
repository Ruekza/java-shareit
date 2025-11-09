package ru.practicum.shareit.user.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
