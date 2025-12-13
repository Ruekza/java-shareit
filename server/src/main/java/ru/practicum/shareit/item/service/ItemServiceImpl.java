package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOwner;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ItemRequestRepository requestRepository;

    public ItemServiceImpl(ItemRepository itemRepository, UserServiceImpl userService, CommentRepository commentRepository, UserRepository userRepository,
                           BookingRepository bookingRepository, ItemRequestRepository requestRepository) {
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    @Transactional
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным id не существует"));
        ItemRequest request = null;
      if (itemDto.getRequest() !=null ) {
           request = requestRepository.findById(itemDto.getRequest()).orElseThrow(() -> new EntityNotFoundException("Запрос не найден"));
        }
        Item item = ItemMapper.toItem(itemDto, user, request);
        Item createdItem = itemRepository.save(item);
        return ItemMapper.toItemDto(createdItem);
    }


    @Override
    @Transactional
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Вещь с указанным id не существует"));
        if (item.getUser().getId().equals(userId)) {
            if (itemDto.getName() != null) {
                item.setName(itemDto.getName());
            }
            if (itemDto.getDescription() != null) {
                item.setDescription(itemDto.getDescription());
            }
            if (itemDto.getAvailable() != null) {
                item.setAvailable(itemDto.getAvailable());
            }
            Item updatedItem = itemRepository.save(item);
            return ItemMapper.toItemDto(updatedItem);
        } else {
            throw new EntityNotFoundException("Редактировать вещь может только ее владелец");
        }
    }

    @Override
    public ItemDtoOwner getItem(Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Вещь с указанным id не существует"));
        ItemDtoOwner itemDtoOwner = ItemMapper.toItemDtoOwner(item);
        if (item.getUser().getId().equals(userId)) {
            itemDtoOwner.setLastBooking(itemRepository.findLastBookingForItem(itemId, LocalDateTime.now()));
            itemDtoOwner.setNextBooking(itemRepository.findNextBookingForItem(itemId, LocalDateTime.now()));
        }
        itemDtoOwner.setComments(CommentMapper.toListCommentDto(commentRepository.findComments(itemId)));
        return itemDtoOwner;

    }

    @Override
    public List<ItemDtoOwner> getOwnerItems(Long userId) {
        if (!userService.isUserExist(userId)) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        } else {
            List<Item> listOfItems = itemRepository.findByUser_Id(userId);
            List<ItemDtoOwner> itemDtoOwners = listOfItems.stream()
                    .map(item -> ItemMapper.toItemDtoOwner(item))
                    .collect(Collectors.toList());
            for (ItemDtoOwner itemDtoOwner : itemDtoOwners) {
                itemDtoOwner.setLastBooking(itemRepository.findLastBookingForItem(itemDtoOwner.getId(), LocalDateTime.now()));
                itemDtoOwner.setNextBooking(itemRepository.findNextBookingForItem(itemDtoOwner.getId(), LocalDateTime.now()));
                itemDtoOwner.setComments(CommentMapper.toListCommentDto(commentRepository.findComments(itemDtoOwner.getId())));
            }
            return itemDtoOwners;

        }
    }

    @Override
    public List<ItemDto> search(Long userId, String text) {
        if (!userService.isUserExist(userId)) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        } else {
            List<Item> listOfItems = itemRepository.findByNameOrDescription(text);
            return listOfItems.stream()
                    .map(item -> ItemMapper.toItemDto(item))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean isItemExist(Long id) {
        return itemRepository.existsById(id);
    }

    @Override
    @Transactional
    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным id не существует"));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Вещь с указанным id не найдена"));

        if (bookingRepository.countUserBookedItem(userId, itemId, LocalDateTime.now()) > 0) {

            Comment comment = CommentMapper.toComment(commentDto, user, item);

            Comment createdComment = commentRepository.save(comment);
            return CommentMapper.toCommentDto(createdComment);
        } else {
            throw new ValidationException("Комментарий может добавить пользователь, бравший вещь в аренду");
        }
    }

}
