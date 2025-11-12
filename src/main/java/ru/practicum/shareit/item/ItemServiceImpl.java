package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    public ItemServiceImpl(ItemRepository itemRepository, UserServiceImpl userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    @Override
    public Map<Long, Item> getTableItems() {
        return itemRepository.getTableItems();
    }

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        if (!userService.isUserExist(userId)) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        } else {
            Item item = ItemMapper.toItem(itemDto);
            Item createdItem = itemRepository.addItem(userId, item);
            return ItemMapper.toItemDto(createdItem);

        }
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        if (!isItemExist(itemId)) {
            throw new EntityNotFoundException("Вещь с указанным id не существует");
        }
        Item item = getTableItems().get(itemId);
        if (item.getOwner().equals(userId)) {
            Item updatedItem = itemRepository.updateItem(userId, itemId, itemDto);
            return ItemMapper.toItemDto(updatedItem);
        } else {
            throw new EntityNotFoundException("Редактировать вещь может только ее владелец");
        }
    }

    @Override
    public ItemDto getItem(Long userId, Long itemId) {
        if (!isItemExist(itemId)) {
            throw new EntityNotFoundException("Вещь с указанным id не существует");
        } else {
            Item item = itemRepository.getItem(userId, itemId);
            return ItemMapper.toItemDto(item);
        }
    }

    @Override
    public List<ItemDto> getOwnerItems(Long userId) {
        if (!userService.isUserExist(userId)) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        } else {
            List<Item> listOfItems = itemRepository.getOwnerItems(userId);
            return listOfItems.stream()
                    .map(item -> ItemMapper.toItemDto(item))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<ItemDto> search(Long userId, String text) {
        if (!userService.isUserExist(userId)) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        } else {
            List<Item> listOfItems = itemRepository.search(userId, text);
            return listOfItems.stream()
                    .map(item -> ItemMapper.toItemDto(item))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean isItemExist(Long id) {
        return getTableItems().containsKey(id);
    }
}
