package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOwner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }

    public static Item toItem(ItemDto itemDto, Long userId) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        User user = new User();
        user.setId(userId);
        item.setUser(user);
        return item;
    }

    public static ItemDtoOwner toItemDtoOwner(Item item) {
        ItemDtoOwner itemDtoOwner = new ItemDtoOwner();
        itemDtoOwner.setId(item.getId());
        itemDtoOwner.setName(item.getName());
        itemDtoOwner.setDescription(item.getDescription());
        itemDtoOwner.setAvailable(item.getAvailable());
        return itemDtoOwner;
    }
}
