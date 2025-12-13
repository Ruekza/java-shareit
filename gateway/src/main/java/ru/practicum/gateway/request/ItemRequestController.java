package ru.practicum.gateway.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.gateway.request.dto.ItemRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestClient client;
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                      @Valid @RequestBody ItemRequestDto itemRequestDto) {
       return client.add(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequests(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return client.getUserRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                             @PathVariable Long requestId) {
      return client.getRequest(userId, requestId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return client.getAllRequests(userId);
    }
}
