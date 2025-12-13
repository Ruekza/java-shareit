package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreateDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "Дата и время начала бронирования должны быть указаны")
    @JsonProperty("start")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "Дата и время окончания бронирования должны быть указаны")
    @JsonProperty("end")
    private LocalDateTime endDate;

    @NotNull(message = "Укажите вещь для бронирования")
    @JsonProperty("itemId")
    private Long item;

    private Long booker;

    private BookingStatus status;
}
