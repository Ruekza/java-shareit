package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOwner;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @SneakyThrows
    @Test
    void add() {
        ItemDto itemDto = new ItemDto(1L, "книга", 2L, "очень захватывающая", true, null, null);
        Long userId = 2L;
        when(itemService.addItem(userId, itemDto)).thenReturn(itemDto);

        mockMvc.perform(post("/items")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())));
        verify(itemService, Mockito.times(1))
                .addItem(userId, itemDto);
    }

    @SneakyThrows
    @Test
    void update() {
        Long userId = 2L;
        Long itemId = 1L;
        ItemDto itemDto = new ItemDto(itemId, "книга", userId, "очень захватывающая", true, null, null);
        when(itemService.updateItem(anyLong(), anyLong(), any())).thenReturn(itemDto);

        mockMvc.perform(patch("/items/{itemId}", itemId, userId, itemDto)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(itemDto))
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())));

        verify(itemService, Mockito.times(1))
                .updateItem(userId, itemId, itemDto);
    }

    @SneakyThrows
    @Test
    void getItem() {
        Long userId = 5L;
        Long itemId = 8L;
        ItemDtoOwner itemDtoOwner = new ItemDtoOwner(8L, "плеер", 5L, "cd", true, null, null, null);
        when(itemService.getItem(userId, itemId)).thenReturn(itemDtoOwner);

        mockMvc.perform(get("/items/{itemId}", itemId, userId)
                        .contentType("application/json")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDtoOwner.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDtoOwner.getName())))
                .andExpect(jsonPath("$.userId", is(itemDtoOwner.getUserId()), Long.class));

        verify(itemService, Mockito.times(1))
                .getItem(userId, itemId);
    }

    @SneakyThrows
    @Test
    void getOwnerItems() {
        Long userId = 5L;
        ItemDtoOwner item1 = new ItemDtoOwner(8L, "плеер", 5L, "cd", true, null, null, null);
        ItemDtoOwner item2 = new ItemDtoOwner(9L, "наушники", 5L, "usb", true, null, null, null);
        when(itemService.getOwnerItems(userId)).thenReturn(List.of(item1, item2));

        mockMvc.perform(get("/items", userId)
                        .contentType("application/json")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].id", is(item2.getId()), Long.class))
                .andExpect(jsonPath("$[0].id", is(item1.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(item1.getName())))
                .andExpect(jsonPath("$[1].userId", is(item2.getUserId()), Long.class));

        verify(itemService, Mockito.times(1))
                .getOwnerItems(userId);
    }

    @SneakyThrows
    @Test
    void search() {
        ItemDto item1 = new ItemDto(1L, "книга", 2L, "очень захватывающая", true, null, null);
        ItemDto item2 = new ItemDto(2L, "книга", 7L, "сложная", true, null, null);
        Long userId = 3L;
        String text = "книг";
        when(itemService.search(userId, text.toLowerCase())).thenReturn(List.of(item1, item2));

        mockMvc.perform(get("/items/search", userId, text)
                        .contentType("application/json")
                        .param("text", text)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].id", is(item2.getId()), Long.class))
                .andExpect(jsonPath("$[0].id", is(item1.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(item1.getName())))
                .andExpect(jsonPath("$[1].userId", is(item2.getUserId()), Long.class));

        verify(itemService, Mockito.times(1))
                .search(userId, text.toLowerCase());
    }

    @SneakyThrows
    @Test
    void addComment() {
        Long userId = 5L;
        Long itemId = 4L;
        CommentDto commentDto = new CommentDto(1L, "очень легко читается, понравилась", "Bob");
        when(itemService.addComment(userId, itemId, commentDto)).thenReturn(commentDto);

        mockMvc.perform(post("/items/{itemId}/comment", itemId, userId, commentDto)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(commentDto))
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(commentDto.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(commentDto.getText())))
                .andExpect(jsonPath("$.authorName", is(commentDto.getAuthorName())));
        verify(itemService, Mockito.times(1))
                .addComment(userId, itemId, commentDto);
    }
}
