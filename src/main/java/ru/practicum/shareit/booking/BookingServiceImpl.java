package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, ItemRepository itemRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BookingDto addBooking(Long userId, BookingCreateDto bookingCreateDto) {
        if (!userService.isUserExist(userId)) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        } else {
            Item item = itemRepository.findById(bookingCreateDto.getItem()).orElseThrow(() -> new EntityNotFoundException("Вещь с указанным id не найдена"));
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь с указанным id не существует"));
            if (item.getAvailable()) {
                Booking booking = BookingMapper.toBooking(bookingCreateDto, item, user);
                Booking createdBooking = bookingRepository.save(booking);
                return BookingMapper.toBookingDto(createdBooking);
            } else {
                throw new ValidationException("Вещь не подлежит бронированию");
            }
        }
    }

    @Override
    public BookingDto updateBooking(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Бронирование с указанным id не найдено"));
        Item item = itemRepository.findById(booking.getItem().getId()).orElseThrow(() -> new EntityNotFoundException("Вещь с указанным id не найдена"));
        if (item.getUser().getId().equals(userId)) {
            if (!approved) {
                booking.setStatus(BookingStatus.REJECTED);
            } else {
                booking.setStatus(BookingStatus.APPROVED);
            }
            Booking updatedBooking = bookingRepository.save(booking);
            return BookingMapper.toBookingDto(updatedBooking);
        } else {
            throw new ValidationException("Подтвердить бронирование может только владелец вещи");
        }
    }

    @Override
    public BookingDto getBooking(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Бронирование с указанным id не существует"));
        if (booking.getBooker().getId().equals(userId) || booking.getItem().getUser().getId().equals(userId)) {
            return BookingMapper.toBookingDto(booking);
        } else {
            throw new EntityNotFoundException("Просмотр доступен только владельцу вещи или автору бронирования");
        }
    }

    @Override
    public List<BookingDto> getUserBookings(Long userId, String state) {
        List<Booking> bookings = new ArrayList<>();
        LocalDateTime now = null;
        switch (state) {
            case "REJECTED", "WAITING":
                bookings = bookingRepository.getUserBookingByWaitingOrRejected(userId, state);
                break;

            case "ALL":
                bookings = bookingRepository.getAllUserBookings(userId);
                break;

            case "CURRENT":
                now = LocalDateTime.now();
                bookings = bookingRepository.getUserBookingByCurrent(userId, now);
                break;

            case "PAST":
                now = LocalDateTime.now();
                bookings = bookingRepository.getUserBookingByPast(userId, now);
                break;

            case "FUTURE":
                now = LocalDateTime.now();
                bookings = bookingRepository.getUserBookingByFuture(userId, now);
                break;

            default:
                throw new EntityNotFoundException("Такого статуса не существует");
        }
        return bookings.stream()
                .map(booking -> BookingMapper.toBookingDto(booking))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getOwnerBookings(Long userId, String state) {
        if (!itemRepository.findByUser_Id(userId).isEmpty()) {
            List<Booking> bookings = new ArrayList<>();
            LocalDateTime now = null;
            switch (state) {
                case "REJECTED", "WAITING":
                    bookings = bookingRepository.getOwnerBookingByWaitingOrRejected(userId, state);
                    break;

                case "ALL":
                    bookings = bookingRepository.getAllOwnerBooking(userId);
                    break;

                case "CURRENT":
                    now = LocalDateTime.now();
                    bookings = bookingRepository.getOwnerBookingByCurrent(userId, now);
                    break;

                case "PAST":
                    now = LocalDateTime.now();
                    bookings = bookingRepository.getOwnerBookingByPast(userId, now);
                    break;

                case "FUTURE":
                    now = LocalDateTime.now();
                    bookings = bookingRepository.getOwnerBookingByFuture(userId, now);
                    break;

                default:
                    throw new EntityNotFoundException("Такого статуса не существует");
            }
            return bookings.stream()
                    .map(booking -> BookingMapper.toBookingDto(booking))
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Бронирований для вещей текущего пользователя нет");
        }
    }

}
