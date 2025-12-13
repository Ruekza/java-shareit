package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOwner;
import ru.practicum.shareit.item.dto.ItemDtoRequset;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setUserId(item.getUser().getId());
        if (item.getRequest() != null) {
            itemDto.setRequest(item.getRequest().getId());
        } else {
            itemDto.setRequest(null);
        }

        return itemDto;
    }

    public static Item toItem(ItemDto itemDto, User user, ItemRequest request) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setUser(user);
        item.setRequest(request);
        return item;
    }

    public static ItemDtoOwner toItemDtoOwner(Item item) {
        ItemDtoOwner itemDtoOwner = new ItemDtoOwner();
        itemDtoOwner.setId(item.getId());
        itemDtoOwner.setName(item.getName());
        itemDtoOwner.setUserId(item.getUser().getId());
        itemDtoOwner.setDescription(item.getDescription());
        itemDtoOwner.setAvailable(item.getAvailable());
        return itemDtoOwner;
    }

    public static ItemDtoRequset toItemDtoRequest(Item item) {
        ItemDtoRequset itemDtoRequset = new ItemDtoRequset();
        itemDtoRequset.setId(item.getId());
        itemDtoRequset.setName(item.getName());
        itemDtoRequset.setUserId(item.getUser().getId());
        return itemDtoRequset;
    }

    public static List<ItemDtoRequset> toListItemDtoRequest(Iterable<Item> items) {
        List<ItemDtoRequset> dtos = new ArrayList<>();
        for (Item item : items) {
            dtos.add(toItemDtoRequest(item));
        }
        return dtos;
    }
}
