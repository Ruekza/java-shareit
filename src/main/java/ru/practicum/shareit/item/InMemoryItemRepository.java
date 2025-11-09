package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    Map<Long, Item> items = new HashMap<>();
    public Long generatorId = 0L;
    private final ItemMapper mapper = new ItemMapper();


    @Override
    public Map<Long, Item> getTableItems() {
        return items;
    }

    @Override
    public Item addItem(Long userId, ItemDto itemDto) {
        Item item = mapper.toItem(itemDto);
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
    public ItemDto getItem(Long userId, Long itemId) {
        Item item = items.get(itemId);
        ItemDto itemDto = mapper.toItemDto(item);
        return itemDto;
    }

    @Override
    public List<ItemDto> getOwnerItems(Long userId) {
        List<ItemDto> listOfItems = items.values().stream()
                .filter(item -> item.getOwner().equals(userId))
                .map(item -> mapper.toItemDto(item))
                .collect(Collectors.toList());
        return listOfItems;
    }

    @Override
    public List<ItemDto> search(Long userId, String text) {
        if (text == null || text.isBlank()) {
            return Collections.EMPTY_LIST;
        }
        List<ItemDto> listOfItems = items.values().stream()
                .filter(item -> item.getAvailable() != null && item.getAvailable() == true
                        && (item.getName() != null && item.getName().toUpperCase().contains(text.toUpperCase()) || item.getDescription() != null && item.getDescription().toUpperCase().contains(text.toUpperCase())))
                .map(item -> mapper.toItemDto(item))
                .collect(Collectors.toList());
        return listOfItems;
    }

    private Long nextId() {
        return generatorId++;
    }
}
