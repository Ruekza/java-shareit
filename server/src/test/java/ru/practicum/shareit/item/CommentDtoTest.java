package ru.practicum.shareit.item;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.CommentDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentDtoTest {

    private final JacksonTester<CommentDto> json;

    @SneakyThrows
    @Test
    void testCommentDto() {
        CommentDto commentDto = new CommentDto(1L, "крутая вещь", "Иван");

        JsonContent<CommentDto> result = json.write(commentDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("крутая вещь");
        assertThat(result).extractingJsonPathStringValue("$.authorName").isEqualTo("Иван");
    }
}


