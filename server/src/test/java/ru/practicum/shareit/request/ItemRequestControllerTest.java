package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.ItemDtoRequset;
import ru.practicum.shareit.request.controller.ItemRequestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestWithAnswerDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static java.time.LocalDateTime.of;

@WebMvcTest(controllers = ItemRequestController.class)
public class ItemRequestControllerTest {

    @MockBean
    private ItemRequestService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @SneakyThrows
    @Test
    void add() {
        LocalDateTime time = of(2025, 12, 12, 17, 11);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        Long userId = 1L;
        UserDto userDto = new UserDto(1L, "Bob", "ttt@mail.ru");
        ItemRequestDto request = new ItemRequestDto(3L, "нужен микрофон", userDto, time);
        when(service.addRequest(userId, request)).thenReturn(request);

        mockMvc.perform(post("/requests")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request))
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(request.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(request.getDescription())))
                .andExpect(jsonPath("$.created", is(request.getCreated().format(formatter))))
                .andExpect(jsonPath("$.requestor.id", is(request.getRequestor().getId()), Long.class))
                .andExpect(jsonPath("$.requestor.name", is(request.getRequestor().getName())))
                .andExpect(jsonPath("$.requestor.email", is(request.getRequestor().getEmail())));
        verify(service, Mockito.times(1))
                .addRequest(userId, request);
    }

    @SneakyThrows
    @Test
    void getUserRequests() {
        LocalDateTime time = of(2025, 12, 12, 17, 11);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        Long userId = 1L;
        ItemDtoRequset items = new ItemDtoRequset(5L, "чайник", 4L);
        ItemRequestWithAnswerDto request = new ItemRequestWithAnswerDto(3L, "нужен чайник", time, List.of(items));
        when(service.getUserRequests(userId)).thenReturn(List.of(request));

        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(request.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(request.getDescription())))
                .andExpect(jsonPath("$[0].created", is(request.getCreated().format(formatter))))
                .andExpect(jsonPath("$[0].items.[0].id", is(request.getItems().get(0).getId()), Long.class))
                .andExpect(jsonPath("$", hasSize(1)));
        verify(service, Mockito.times(1))
                .getUserRequests(userId);
    }

    @SneakyThrows
    @Test
    void getRequest() {
        LocalDateTime time = of(2025, 12, 12, 17, 11);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        Long userId = 1L;
        Long requestId = 3L;
        ItemDtoRequset items = new ItemDtoRequset(5L, "чайник", 4L);
        ItemRequestWithAnswerDto request = new ItemRequestWithAnswerDto(3L, "нужен чайник", time, List.of(items));
        when(service.getRequest(userId, requestId)).thenReturn(request);
        mockMvc.perform(get("/requests/{requestId}", requestId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(request.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(request.getDescription())))
                .andExpect(jsonPath("$.created", is(request.getCreated().format(formatter))))
                .andExpect(jsonPath("$.items.[0].id", is(request.getItems().get(0).getId()), Long.class));
        verify(service, Mockito.times(1))
                .getRequest(userId, requestId);
    }

    @SneakyThrows
    @Test
    void getAllRequests() {
        Long userId = 1L;
        UserDto userDto = new UserDto(1L, "Bob", "ttt@mail.ru");
        ItemRequestDto request1 = new ItemRequestDto(3L, "нужен микрофон", userDto, LocalDateTime.now());
        ItemRequestDto request2 = new ItemRequestDto(4L, "нужен стол", userDto, LocalDateTime.now());
        when(service.getAllRequests(userId)).thenReturn(List.of(request1, request2));

        mockMvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].id", is(request2.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(request1.getDescription())))
                .andExpect(jsonPath("$[1].requestor.id", is(request2.getRequestor().getId()), Long.class))
                .andExpect(jsonPath("$[1].requestor.name", is(request2.getRequestor().getName())))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(service, Mockito.times(1))
                .getAllRequests(userId);
    }
}
