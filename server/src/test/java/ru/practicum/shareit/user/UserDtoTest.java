package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.user.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDtoTest {

    private final JacksonTester<UserDto> json;

    @SneakyThrows
    @Test
    void testUserDto() {
        UserDto userDto = new UserDto(1L, "Olga", "rrr@mail.ru");

        JsonContent<UserDto> result = json.write(userDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Olga");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("rrr@mail.ru");
    }
}
