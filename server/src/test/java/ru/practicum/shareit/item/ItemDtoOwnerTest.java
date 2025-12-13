package ru.practicum.shareit.item;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemDtoOwner;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemDtoOwnerTest {

    private final JacksonTester<ItemDtoOwner> json;

    @SneakyThrows
    @Test
    void testItemDtoOwner() {
        ItemDtoOwner itemDtoOwner = new ItemDtoOwner(8L, "плеер", 5L, "cd", true, null, null, null);

        JsonContent<ItemDtoOwner> result = json.write(itemDtoOwner);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(8);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("плеер");
        assertThat(result).extractingJsonPathNumberValue("$.userId").isEqualTo(5);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("cd");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isEqualTo(true);
    }

}
