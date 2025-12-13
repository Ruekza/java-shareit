package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestWithAnswerDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {
    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setRequestor(UserMapper.toUserDto(itemRequest.getRequestor()));
        itemRequestDto.setCreated(itemRequest.getCreated());
        return itemRequestDto;
    }

    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto, User user) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setRequestor(user);
        return itemRequest;
    }

    public static List<ItemRequestDto> toListItemRequestDto(Iterable<ItemRequest> requests) {
        List<ItemRequestDto> dtos = new ArrayList<>();
        for (ItemRequest request : requests) {
            dtos.add(toItemRequestDto(request));
        }
        return dtos;
    }

    public static ItemRequestWithAnswerDto toItemRequestWithAnswerDto(ItemRequest itemRequest) {
        ItemRequestWithAnswerDto itemRequestWithAnswerDto = new ItemRequestWithAnswerDto();
        itemRequestWithAnswerDto.setId(itemRequest.getId());
        itemRequestWithAnswerDto.setDescription(itemRequest.getDescription());
        itemRequestWithAnswerDto.setCreated(itemRequest.getCreated());
        return itemRequestWithAnswerDto;
    }

    public static List<ItemRequestWithAnswerDto> toListItemRequestWithAnswerDto(Iterable<ItemRequest> requests) {
        List<ItemRequestWithAnswerDto> dtos = new ArrayList<>();
        for (ItemRequest request : requests) {
            dtos.add(toItemRequestWithAnswerDto(request));
        }
        return dtos;
    }
}
