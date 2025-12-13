package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestDtoTest {

    private final JacksonTester<ItemRequestDto> json;

    @SneakyThrows
    @Test
    void testItemRequestDto() {
        LocalDateTime time = of(2025, 12, 12, 17, 11);
        UserDto userDto = new UserDto(1L, "Bob", "ttt@mail.ru");
        ItemRequestDto request = new ItemRequestDto(3L, "нужен микрофон", userDto, time);

        JsonContent<ItemRequestDto> result = json.write(request);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(3);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("нужен микрофон");
        String formattedTime = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(formattedTime);
    }
}
