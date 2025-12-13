package ru.practicum.shareit.item;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOwner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceImplTest {

    private final EntityManager em;
    private final ItemService itemService;
    private final UserService userService;
    private final BookingService bookingService;

    @Test
    void getItem() {
        UserDto userDto = new UserDto(null, "Anna", "anna@mail.ru");
        UserDto createdUserDto = userService.createUser(userDto);

        ItemDto itemDto = new ItemDto(null, "книга", createdUserDto.getId(), "очень захватывающая", false, null, null);
        ItemDto createdItemDto = itemService.addItem(createdUserDto.getId(), itemDto);

        ItemDtoOwner itemDtoOwner = itemService.getItem(createdItemDto.getId(), createdItemDto.getId());

        Item item = em.createQuery("Select i from Item i where i.id = :id", Item.class)
                .setParameter("id", createdItemDto.getId())
                .getSingleResult();

        assertThat(item.getId(), notNullValue());
        assertThat(item.getName(), equalTo(itemDtoOwner.getName()));
        assertThat(item.getAvailable(), equalTo(itemDtoOwner.getAvailable()));
        assertThat(item.getUser().getId(), equalTo(itemDtoOwner.getUserId()));
    }

    @Test
    void addItem() {
        UserDto userDto = new UserDto(null, "Anna", "anna@mail.ru");
        UserDto createdUserDto = userService.createUser(userDto);

        ItemDto itemDto = new ItemDto(null, "книга", createdUserDto.getId(), "очень захватывающая", false, null, null);
        ItemDto createdItemDto = itemService.addItem(createdUserDto.getId(), itemDto);

        Item item = em.createQuery("Select i from Item i where i.id = :id", Item.class)
                .setParameter("id", createdItemDto.getId())
                .getSingleResult();

        assertThat(item.getId(), notNullValue());
        assertThat(item.getName(), equalTo(createdItemDto.getName()));
        assertThat(item.getAvailable(), equalTo(createdItemDto.getAvailable()));
        assertThat(item.getUser().getId(), equalTo(createdItemDto.getUserId()));

    }

    @Test
    void updateItem() {
        UserDto userDto = new UserDto(null, "Anna", "anna@mail.ru");
        UserDto createdUserDto = userService.createUser(userDto);

        ItemDto itemDtoOld = new ItemDto(null, "книга", createdUserDto.getId(), "очень захватывающая", false, null, null);
        ItemDto createdItemDto = itemService.addItem(createdUserDto.getId(), itemDtoOld);

        ItemDto itemDtoNew = new ItemDto();
        itemDtoNew.setAvailable(true);
        itemDtoNew.setName("книжечка");
        itemDtoNew.setDescription("крутая");

        ItemDto updateItemDto = itemService.updateItem(createdItemDto.getId(), createdItemDto.getId(), itemDtoNew);

        Item item = em.createQuery("Select i from Item i where i.id = :id", Item.class)
                .setParameter("id", createdItemDto.getId())
                .getSingleResult();

        assertThat(item.getId(), notNullValue());
        assertThat(item.getName(), equalTo(updateItemDto.getName()));
        assertThat(item.getAvailable(), equalTo(updateItemDto.getAvailable()));
        assertThat(item.getUser().getId(), equalTo(updateItemDto.getUserId()));
    }

}
