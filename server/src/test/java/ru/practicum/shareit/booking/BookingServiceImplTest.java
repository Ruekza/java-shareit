package ru.practicum.shareit.booking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceImplTest {

    private final EntityManager em;
    private final BookingService bookingService;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemRequestService itemRequestService;

    @Test
    void getBooking() {
        UserDto user = new UserDto(null, "Bob", "ppp@mail.ru");
        UserDto userDto = userService.createUser(user);

        ItemDto item = new ItemDto(null, "фотокамера", userDto.getId(), "Sony", true, null, null);
        ItemDto itemDto = itemService.addItem(userDto.getId(), item);

        LocalDateTime start = LocalDateTime.of(2025, 12, 15, 15, 00, 00);
        LocalDateTime end = LocalDateTime.of(2025, 12, 18, 15, 00, 00);
        BookingCreateDto bookingCreateDto = new BookingCreateDto(null, start, end, itemDto.getId(), userDto.getId(), BookingStatus.WAITING);
        BookingDto bookingDto = bookingService.addBooking(userDto.getId(), bookingCreateDto);

        BookingDto gotBookingDto = bookingService.getBooking(userDto.getId(), bookingDto.getId());

        TypedQuery<Booking> query = em.createQuery("Select b from Booking b where b.id = :id", Booking.class);
        Booking booking = query.setParameter("id", bookingDto.getId())
                .getSingleResult();

        assertThat(booking.getId(), notNullValue());
        assertThat(booking.getItem().getId(), equalTo(gotBookingDto.getItem().getId()));
        assertThat(booking.getBooker().getId(), equalTo(gotBookingDto.getBooker().getId()));
    }
}
