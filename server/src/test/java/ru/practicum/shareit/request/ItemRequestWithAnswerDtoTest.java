package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemDtoRequset;
import ru.practicum.shareit.request.dto.ItemRequestWithAnswerDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestWithAnswerDtoTest {

    private final JacksonTester<ItemRequestWithAnswerDto> json;

    @SneakyThrows
    @Test
    void testItemRequestWithAnswerDto() {
        LocalDateTime time = of(2025, 12, 12, 17, 11);

        ItemDtoRequset itemDtoRequset = new ItemDtoRequset(7L, "зонт", 22L);

        ItemRequestWithAnswerDto request = new ItemRequestWithAnswerDto(8L, "нужен зонт", time, List.of(itemDtoRequset));

        System.out.println(request);
        JsonContent<ItemRequestWithAnswerDto> result = json.write(request);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(8);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("нужен зонт");
        String formattedTime = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(formattedTime);
        assertThat(result).extractingJsonPathNumberValue("$.items[0].id").isEqualTo(7);
    }
}
