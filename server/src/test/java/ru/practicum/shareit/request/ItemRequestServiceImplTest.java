package ru.practicum.shareit.request;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestServiceImplTest {

    private final EntityManager em;
    private final ItemRequestService itemRequestService;
    private final UserService userService;

    @Test
    void addRequest() {
        LocalDateTime time = LocalDateTime.now();
        UserDto userDto = new UserDto(null, "Ivan", "ivan@mail.ru");
        UserDto createdUserDto = userService.createUser(userDto);
        Long userId = createdUserDto.getId();
        ItemRequestDto request = new ItemRequestDto(null, "нужен микрофон", createdUserDto, time);

        ItemRequestDto createdRequest = itemRequestService.addRequest(userId, request);

        TypedQuery<ItemRequest> query = em.createQuery("Select req from ItemRequest req where req.id = :id", ItemRequest.class);
        ItemRequest itemRequest = query.setParameter("id", createdRequest.getId())
                .getSingleResult();

        assertThat(itemRequest.getId(), notNullValue());
        assertThat(itemRequest.getDescription(), equalTo(createdRequest.getDescription()));
        assertThat(itemRequest.getRequestor().getId(), equalTo(createdRequest.getRequestor().getId()));
    }
}
