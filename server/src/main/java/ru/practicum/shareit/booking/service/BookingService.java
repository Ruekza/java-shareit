package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {

    BookingDto addBooking(Long userId, BookingCreateDto bookingCreateDto);

    BookingDto updateBooking(Long userId, Long bookingId, Boolean approved);

    BookingDto getBooking(Long userId, Long bookingId);

    List<BookingDto> getUserBookings(Long userId, String state);

    List<BookingDto> getOwnerBookings(Long userId, String state);
}
