package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestWithAnswerDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto addRequest(Long userId, ItemRequestDto itemRequestDto);

    List<ItemRequestWithAnswerDto> getUserRequests(Long userId);

    ItemRequestWithAnswerDto getRequest(Long userId, Long requestId);

    List<ItemRequestDto> getAllRequests(Long userId);

}
