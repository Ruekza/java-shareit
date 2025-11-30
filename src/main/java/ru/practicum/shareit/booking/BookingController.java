package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto add(@RequestHeader(X_SHARER_USER_ID) Long userId,
                          @Valid @RequestBody BookingCreateDto bookingCreateDto) {
        return bookingService.addBooking(userId, bookingCreateDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto update(@RequestHeader(X_SHARER_USER_ID) Long userId,
                             @PathVariable Long bookingId,
                             @RequestParam Boolean approved) {
        return bookingService.updateBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                 @PathVariable Long bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getUserBookings(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                            @RequestParam(name = "state", required = false, defaultValue = "ALL") String state) {
        state = state.toUpperCase();
        return bookingService.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getOwnerBookings(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                             @RequestParam(name = "state", required = false, defaultValue = "ALL") String state) {
        state = state.toUpperCase();
        return bookingService.getOwnerBookings(userId, state);
    }

}
