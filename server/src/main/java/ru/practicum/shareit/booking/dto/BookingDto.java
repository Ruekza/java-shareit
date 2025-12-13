package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
