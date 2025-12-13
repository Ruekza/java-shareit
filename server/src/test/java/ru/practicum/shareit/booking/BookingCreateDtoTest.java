package ru.practicum.shareit.booking;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingCreateDtoTest {

    private final JacksonTester<BookingCreateDto> json;

    @SneakyThrows
    @Test
    void testBookingCreateDto() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 15, 15, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 12, 18, 15, 00, 00);
        BookingCreateDto bookingCreateDto = new BookingCreateDto(1L, start, end, 4L, 8L, BookingStatus.WAITING);

        JsonContent<BookingCreateDto> result = json.write(bookingCreateDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        String formattedStart = start.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String formattedEnd = end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(formattedStart);
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(formattedEnd);
        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(4);
        assertThat(result).extractingJsonPathNumberValue("$.booker").isEqualTo(8);
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(BookingStatus.WAITING.name());
    }

}
