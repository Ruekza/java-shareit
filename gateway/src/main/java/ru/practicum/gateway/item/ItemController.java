package ru.practicum.gateway.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.gateway.item.dto.CommentDto;
import ru.practicum.gateway.item.dto.ItemDto;


import java.util.Collections;


@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemClient client;
    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";


    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                      @Valid @RequestBody ItemDto itemDto) {
        return client.add(userId, itemDto);

    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                         @PathVariable Long itemId,
                                         @RequestBody ItemDto itemDto) {
        return client.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                          @PathVariable Long itemId) {
        return client.getItem(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getOwnerItems(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return client.getOwnerItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                         @RequestParam String text) {
        if (text == null || text.isBlank()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        } else {
            return client.search(userId, text.toLowerCase());
        }
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                             @PathVariable Long itemId,
                                             @Valid @RequestBody CommentDto commentDto) {
        return client.addComment(userId, itemId, commentDto);
    }

}
