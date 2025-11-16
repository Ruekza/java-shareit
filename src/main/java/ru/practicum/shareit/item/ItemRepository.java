package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Map;

public interface ItemRepository {
    Map<Long, Item> getTableItems();

    Item addItem(Long userId, Item item);

    Item updateItem(Long userId, Long itemId, ItemDto itemDto);

    Item getItem(Long userId, Long itemId);

    List<Item> getOwnerItems(Long userId);

    List<Item> search(Long userId, String text);
}
