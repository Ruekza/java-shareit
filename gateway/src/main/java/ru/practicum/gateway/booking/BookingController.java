package ru.practicum.gateway.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.gateway.booking.dto.BookingCreateDto;


@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    private final BookingClient client;

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                      @Valid @RequestBody BookingCreateDto bookingCreateDto) {
        return client.add(userId, bookingCreateDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> update(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                         @PathVariable Long bookingId,
                                         @RequestParam(name = "approved") Boolean approved) {
        return client.update(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                             @PathVariable Long bookingId) {
        return client.getBooking(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserBookings(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                  @RequestParam(name = "state", defaultValue = "ALL") String state) {
        state = state.toUpperCase();
        return client.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getOwnerBookings(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                   @RequestParam(name = "state", defaultValue = "ALL") String state) {
        state = state.toUpperCase();
        return client.getOwnerBookings(userId, state);

    }

}
