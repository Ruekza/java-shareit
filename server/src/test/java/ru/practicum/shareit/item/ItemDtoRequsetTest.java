package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemDtoRequset;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemDtoRequsetTest {

    private final JacksonTester<ItemDtoRequset> json;

    @SneakyThrows
    @Test
    void testItemDtoRequset() {
        ItemDtoRequset itemDtoRequset = new ItemDtoRequset(99L, "нужен чемодан", 44L);

        JsonContent<ItemDtoRequset> result = json.write(itemDtoRequset);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(99);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("нужен чемодан");
        assertThat(result).extractingJsonPathNumberValue("$.userId").isEqualTo(44);
    }

}
