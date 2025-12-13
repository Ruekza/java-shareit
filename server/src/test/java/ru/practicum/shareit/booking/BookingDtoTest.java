package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingDtoTest {

    private final JacksonTester<BookingDto> json;

    @SneakyThrows
    @Test
    void bookingDtoTest() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 15, 15, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 12, 18, 15, 00, 00);
        ItemDto item = new ItemDto(4L, "фотокамера", 18L, "Sony", true, null, null);
        UserDto user = new UserDto(5L, "Bob", "ppp@mail.ru");
        BookingDto bookingDto = new BookingDto(1L, start, end, item, user, BookingStatus.WAITING);

        JsonContent<BookingDto> result = json.write(bookingDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        String formattedStart = start.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String formattedEnd = end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(formattedStart);
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(formattedEnd);
        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(4);
        assertThat(result).extractingJsonPathNumberValue("$.booker.id").isEqualTo(5);
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(BookingStatus.WAITING.name());
    }
}
