package ru.practicum.shareit.item;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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

        System.out.println(itemDtoOwner);
        System.out.println(item);
    }
}
