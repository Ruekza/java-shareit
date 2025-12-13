package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @SneakyThrows
    @Test
    void getAllUsers() {
        UserDto userDto1 = new UserDto(1L, "Bob", "bbb@mail.ru");
        UserDto userDto2 = new UserDto(2L, "Anna", "aaa@mail.ru");
        when(userService.getAllUsers()).thenReturn(List.of(userDto1, userDto2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].id", is(userDto2.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(userDto1.getName())))
                .andExpect(jsonPath("$[1].email", is(userDto2.getEmail())))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(userService, Mockito.times(1))
                .getAllUsers();
    }

    @SneakyThrows
    @Test
    void createUser() {
        UserDto userDto = new UserDto(1L, "Bob", "ttt@mail.ru");
        when(userService.createUser(userDto)).thenReturn(userDto);

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())));
        verify(userService, Mockito.times(1))
                .createUser(userDto);
    }

    @SneakyThrows
    @Test
    void updateUser() {
        Long userId = 2L;
        UserDto userToUpdate = new UserDto(userId, "Tom", "ppp@mail.ru");
        when(userService.updateUser(anyLong(), any())).thenReturn(userToUpdate);

        mockMvc.perform(patch("/users/{id}", userId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(userToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userToUpdate.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(userToUpdate.getName())))
                .andExpect(jsonPath("$.email", is(userToUpdate.getEmail())));

        verify(userService, Mockito.times(1))
                .updateUser(userId, userToUpdate);
    }

    @SneakyThrows
    @Test
    void deleteUser() {
        UserDto userDto = new UserDto(1L, "Bob", "bbb@mail.ru");
        Long userId = 1L;
        mockMvc.perform(delete("/users/{id}", userId)
                        .contentType("application/json"))
                .andExpect(status().isOk());
        verify(userService, Mockito.times(1))
                .deleteUser(userId);
    }

    @SneakyThrows
    @Test
    void getUser() {
        Long userId = 1L;
        UserDto userDto = new UserDto(1L, "Bob", "bbb@mail.ru");
        when(userService.getUser(userId)).thenReturn(userDto);

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())));
        verify(userService).getUser(userId);
    }
}
