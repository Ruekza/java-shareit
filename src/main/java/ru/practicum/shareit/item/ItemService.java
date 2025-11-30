package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOwner;

import java.util.List;

public interface ItemService {

    ItemDto addItem(Long userId, ItemDto itemDto);

    ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    ItemDtoOwner getItem(Long userId, Long itemId);

    List<ItemDtoOwner> getOwnerItems(Long userId);

    List<ItemDto> search(Long userId, String text);

    boolean isItemExist(Long id);

    CommentDto addComment(Long userId, Long itemId, CommentDto commentDto);
}
