package ru.practicum.shareit.request.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestWithAnswerDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ItemRequestDto add(@RequestHeader(X_SHARER_USER_ID) Long userId,
                              @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestService.addRequest(userId, itemRequestDto);
    }

    @GetMapping
    public List<ItemRequestWithAnswerDto> getUserRequests(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemRequestService.getUserRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestWithAnswerDto getRequest(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                     @PathVariable Long requestId) {
        return itemRequestService.getRequest(userId, requestId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequests(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemRequestService.getAllRequests(userId);
    }
}
