package ru.practicum.gateway.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.gateway.booking.dto.BookingCreateDto;
import ru.practicum.gateway.client.BaseClient;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String URL = "/bookings";

    @Value("${server.host}")
    private String host;

    @Autowired
    public BookingClient(@Value("${server.host}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + URL))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> add(Long userId, BookingCreateDto bookingCreateDto) {
        return post(host + URL, userId, bookingCreateDto);
    }

    public ResponseEntity<Object> update(Long userId, Long bookingId, Boolean approved) {
        Map<String, Object> parameters = Map.of("approved", approved);
        return patch("/" + bookingId + "?approved=" + approved, userId, parameters);
    }

    public ResponseEntity<Object> getBooking(Long userId, Long bookingId) {
        return get(host + URL + "/" + bookingId, userId);
    }

    public ResponseEntity<Object> getUserBookings(Long userId, String state) {
        Map<String, Object> parameters = Map.of("state", state);
        return get(host + URL + "?state=" + state, userId, parameters);
    }

    public ResponseEntity<Object> getOwnerBookings(Long userId, String state) {
        Map<String, Object> parameters = Map.of("state", state);
        return get("/owner" + "?state=" + state, userId, parameters);
    }

}
