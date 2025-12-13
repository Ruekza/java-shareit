package ru.practicum.shareit.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestWithAnswerDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ItemRequestServiceImpl(ItemRequestRepository requestRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemRequestDto addRequest(Long userId, ItemRequestDto itemRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным id не найден"));
        ItemRequest itemRequest = RequestMapper.toItemRequest(itemRequestDto, user);
        ItemRequest createdRequest = requestRepository.save(itemRequest);
        return RequestMapper.toItemRequestDto(createdRequest);
    }

    @Override
    public List<ItemRequestWithAnswerDto> getUserRequests(Long userId) {
        List<ItemRequest> requests = requestRepository.getUserRequests(userId);

        List<ItemRequestWithAnswerDto> dtos = RequestMapper.toListItemRequestWithAnswerDto(requests);
        for (ItemRequestWithAnswerDto itemRequestWithAnswerDto : dtos) {
            itemRequestWithAnswerDto.setItems(ItemMapper.toListItemDtoRequest(itemRepository.findByRequest_Id(itemRequestWithAnswerDto.getId())));
        }
        return dtos;
    }

    @Override
    public ItemRequestWithAnswerDto getRequest(Long userId, Long requestId) {
        ItemRequest itemRequest = requestRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException("Такого запроса нет"));
        ItemRequestWithAnswerDto request = RequestMapper.toItemRequestWithAnswerDto(itemRequest);
        request.setItems(ItemMapper.toListItemDtoRequest(itemRepository.findByRequest_Id(requestId)));
        return request;
    }

    @Override
    public List<ItemRequestDto> getAllRequests(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным id не найден"));
        List<ItemRequest> requests = requestRepository.getAllRequest(userId);
        return RequestMapper.toListItemRequestDto(requests);
    }
}
