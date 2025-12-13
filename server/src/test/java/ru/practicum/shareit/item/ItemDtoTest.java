package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemDtoTest {

    private final JacksonTester<ItemDto> json;

    @SneakyThrows
    @Test
    void testItemDto() {
        ItemDto itemDto = new ItemDto(1L, "книга", 2L, "очень захватывающая", true, null, null);

        JsonContent<ItemDto> result = json.write(itemDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("книга");
        assertThat(result).extractingJsonPathNumberValue("$.userId").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("очень захватывающая");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isEqualTo(true);
        assertThat(result).extractingJsonPathStringValue("$.comments").isNull();
        assertThat(result).extractingJsonPathStringValue("$.request").isNull();
    }
}
