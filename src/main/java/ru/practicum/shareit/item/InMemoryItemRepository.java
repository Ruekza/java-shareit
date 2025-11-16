package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private Map<Long, Item> items = new HashMap<>();
    private Long generatorId = 0L;

    @Override
    public Map<Long, Item> getTableItems() {
        return new HashMap<>(items);
    }

    @Override
    public Item addItem(Long userId, Item item) {
        item.setId(nextId());
        item.setOwner(userId);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = items.get(itemId);
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        return item;
    }

    @Override
    public Item getItem(Long userId, Long itemId) {
        Item item = items.get(itemId);
        return item;
    }

    @Override
    public List<Item> getOwnerItems(Long userId) {
        List<Item> listOfItems = items.values().stream()
                .filter(item -> item.getOwner().equals(userId))
                .collect(Collectors.toList());
        return listOfItems;
    }

    @Override
    public List<Item> search(Long userId, String text) {
        List<Item> listOfItems = items.values().stream()
                .filter(item -> item.getAvailable() != null && item.getAvailable() == true
                        && (item.getName() != null && item.getName().toUpperCase().contains(text.toUpperCase()) || item.getDescription() != null && item.getDescription().toUpperCase().contains(text.toUpperCase())))
                .collect(Collectors.toList());
        return listOfItems;
    }

    private Long nextId() {
        return generatorId++;
    }
}
