package ru.practicum.gateway.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import ru.practicum.gateway.booking.BookingStatus;
import ru.practicum.gateway.item.dto.ItemDto;
import ru.practicum.gateway.user.dto.UserDto;


import java.time.LocalDateTime;

@Data
@ToString
public class BookingDto {
    private Long id;

    @JsonProperty("start")
    private LocalDateTime startDate;

    @JsonProperty("end")
    private LocalDateTime endDate;

    private ItemDto item;

    private UserDto booker;

    private BookingStatus status;
}
