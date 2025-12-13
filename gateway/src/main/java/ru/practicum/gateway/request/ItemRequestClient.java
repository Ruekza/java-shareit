package ru.practicum.gateway.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.gateway.client.BaseClient;
import ru.practicum.gateway.request.dto.ItemRequestDto;

@Service
public class ItemRequestClient extends BaseClient {
    private static final String URL = "/requests";

    @Value("${server.host}")
    private String host;

//    public ItemRequestClient() {
//        super(new RestTemplate());
//    }

    @Autowired
    public ItemRequestClient(@Value("${server.host}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + URL))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> add(Long userId, ItemRequestDto itemRequestDto) {
        return post(host + URL, userId, itemRequestDto);
    }

    public ResponseEntity<Object> getUserRequests(Long userId) {
        return get(host + URL, userId);
    }

    public ResponseEntity<Object> getRequest(Long userId, Long requestId) {
        return get(host + URL + "/" + requestId, userId);
    }

    public ResponseEntity<Object> getAllRequests(Long userId) {
        return get(host + URL + "/all", userId);
    }

}
