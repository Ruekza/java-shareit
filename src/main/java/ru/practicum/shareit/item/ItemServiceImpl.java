package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserServiceImpl;

import java.util.List;
import java.util.Map;

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
    public Item addItem(Long userId, ItemDto itemDto) {
        if (!userService.isUserExist(userId)) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        } else {
            return itemRepository.addItem(userId, itemDto);
        }
    }

    @Override
    public Item updateItem(Long userId, Long itemId, ItemDto itemDto) {
        if (!isItemExist(itemId)) {
            throw new EntityNotFoundException("Вещь с указанным id не существует");
        }
        Item item = getTableItems().get(itemId);
        if (item.getOwner().equals(userId)) {
            return itemRepository.updateItem(userId, itemId, itemDto);
        } else {
            throw new EntityNotFoundException("Редактировать вещь может только ее владелец");
        }
    }

    @Override
    public ItemDto getItem(Long userId, Long itemId) {
        if (!isItemExist(itemId)) {
            throw new EntityNotFoundException("Вещь с указанным id не существует");
        } else {
            return itemRepository.getItem(userId, itemId);
        }
    }

    @Override
    public List<ItemDto> getOwnerItems(Long userId) {
        if (!userService.isUserExist(userId)) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        } else {
            return itemRepository.getOwnerItems(userId);
        }
    }

    @Override
    public List<ItemDto> search(Long userId, String text) {
        if (!userService.isUserExist(userId)) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        } else {
            return itemRepository.search(userId, text);
        }
    }

    @Override
    public boolean isItemExist(Long id) {
        return getTableItems().containsKey(id);
    }
}
