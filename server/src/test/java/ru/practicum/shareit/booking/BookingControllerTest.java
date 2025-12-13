package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @SneakyThrows
    @Test
    void add() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 15, 15, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 12, 18, 15, 00, 00);
        Long userId = 5L;
        ItemDto item = new ItemDto(4L, "фотокамера", 18L, "Sony", true, null, null);
        UserDto user = new UserDto(userId, "Bob", "ppp@mail.ru");
        BookingCreateDto bookingCreateDto = new BookingCreateDto(1L, start, end, 4L, userId, BookingStatus.WAITING);
        BookingDto bookingDto = new BookingDto(1L, start, end, item, user, BookingStatus.WAITING);
        when(bookingService.addBooking(userId, bookingCreateDto)).thenReturn(bookingDto);

        mockMvc.perform(post("/bookings")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(bookingCreateDto))
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.status", is(bookingDto.getStatus().toString())))
                .andExpect(jsonPath("$.booker.id", is(bookingDto.getBooker().getId()), Long.class))
                .andExpect(jsonPath("$.booker.name", is(bookingDto.getBooker().getName())))
                .andExpect(jsonPath("$.item.id", is(bookingDto.getItem().getId()), Long.class))
                .andExpect(jsonPath("$.item.name", is(bookingDto.getItem().getName())));

        verify(bookingService, Mockito.times(1))
                .addBooking(userId, bookingCreateDto);
    }

    @SneakyThrows
    @Test
    void update() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 15, 15, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 12, 18, 15, 00, 00);
        Long userId = 5L;
        ItemDto item = new ItemDto(4L, "фотокамера", 18L, "Sony", true, null, null);
        UserDto user = new UserDto(userId, "Bob", "ppp@mail.ru");
        BookingDto bookingDto = new BookingDto(1L, start, end, item, user, BookingStatus.WAITING);
        Long bookingId = 1L;
        Boolean approved = true;
        when(bookingService.updateBooking(userId, bookingId, approved)).thenReturn(bookingDto);

        mockMvc.perform(patch("/bookings/{bookingId}", bookingId, userId, approved)
                        .contentType("application/json")
                        .param("approved", String.valueOf(approved))
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.status", is(bookingDto.getStatus().toString())))
                .andExpect(jsonPath("$.booker.id", is(bookingDto.getBooker().getId()), Long.class))
                .andExpect(jsonPath("$.booker.name", is(bookingDto.getBooker().getName())))
                .andExpect(jsonPath("$.item.id", is(bookingDto.getItem().getId()), Long.class))
                .andExpect(jsonPath("$.item.name", is(bookingDto.getItem().getName())));

        verify(bookingService, Mockito.times(1))
                .updateBooking(userId, bookingId, approved);
    }

    @SneakyThrows
    @Test
    void getBooking() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 15, 15, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 12, 18, 15, 00, 00);
        Long userId = 5L;
        ItemDto item = new ItemDto(4L, "фотокамера", 18L, "Sony", true, null, null);
        UserDto user = new UserDto(userId, "Bob", "ppp@mail.ru");
        BookingDto bookingDto = new BookingDto(1L, start, end, item, user, BookingStatus.WAITING);
        Long bookingId = 1L;
        when(bookingService.getBooking(userId, bookingId)).thenReturn(bookingDto);

        mockMvc.perform(get("/bookings/{bookingId}", bookingId, userId)
                        .contentType("application/json")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.booker.id", is(bookingDto.getBooker().getId()), Long.class))
                .andExpect(jsonPath("$.item.id", is(bookingDto.getItem().getId()), Long.class));

        verify(bookingService, Mockito.times(1))
                .getBooking(userId, bookingId);
    }

    @SneakyThrows
    @Test
    void getUserBookings() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 15, 15, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 12, 18, 15, 00, 00);
        Long userId = 5L;
        ItemDto item = new ItemDto(4L, "фотокамера", 18L, "Sony", true, null, null);
        UserDto user = new UserDto(userId, "Bob", "ppp@mail.ru");
        BookingDto bookingDto = new BookingDto(1L, start, end, item, user, BookingStatus.WAITING);
        String state = "ALL";
        when(bookingService.getUserBookings(userId, state)).thenReturn(List.of(bookingDto));

        mockMvc.perform(get("/bookings", userId, state)
                        .contentType("application/json")
                        .param("state", state)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].booker.id", is(bookingDto.getBooker().getId()), Long.class))
                .andExpect(jsonPath("$[0].item.id", is(bookingDto.getItem().getId()), Long.class));

        verify(bookingService, Mockito.times(1))
                .getUserBookings(userId, state.toUpperCase());
    }

    @SneakyThrows
    @Test
    void getOwnerBookings() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 15, 15, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 12, 18, 15, 00, 00);
        Long userId = 5L;
        ItemDto item = new ItemDto(4L, "фотокамера", 18L, "Sony", true, null, null);
        UserDto user = new UserDto(userId, "Bob", "ppp@mail.ru");
        BookingDto bookingDto = new BookingDto(1L, start, end, item, user, BookingStatus.WAITING);
        String state = "ALL";
        when(bookingService.getOwnerBookings(userId, state)).thenReturn(List.of(bookingDto));

        mockMvc.perform(get("/bookings/owner", userId, state)
                        .contentType("application/json")
                        .param("state", state)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bookingDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].booker.id", is(bookingDto.getBooker().getId()), Long.class))
                .andExpect(jsonPath("$[0].item.id", is(bookingDto.getItem().getId()), Long.class));

        verify(bookingService, Mockito.times(1))
                .getOwnerBookings(userId, state.toUpperCase());
    }
}
