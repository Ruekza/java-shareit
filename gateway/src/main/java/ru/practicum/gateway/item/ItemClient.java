package ru.practicum.gateway.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.gateway.client.BaseClient;
import ru.practicum.gateway.item.dto.CommentDto;
import ru.practicum.gateway.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String URL = "/items";

    @Value("${server.host}")
    private String host;

//    public ItemClient() {
//        super(new RestTemplate());
//    }

    @Autowired
    public ItemClient(@Value("${server.host}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + URL))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> add(Long userId, ItemDto itemDto) {
        return post(host + URL, userId, itemDto);
    }

    public ResponseEntity<Object> update(Long userId, Long itemId, ItemDto itemDto) {
        return patch(host + URL + "/" + itemId, userId, itemDto);

    }

    public ResponseEntity<Object> getItem(Long userId, Long itemId) {
        return get(host + URL + "/" + itemId, userId);
    }

    public ResponseEntity<Object> getOwnerItems(Long userId) {
        return get(host + URL, userId);
    }

    public ResponseEntity<Object> search(Long userId, String text) {
        Map<String, Object> parameters = Map.of("text", text);
        return get(host + URL + "/search" + "?text={text}", userId, parameters);
    }

    public ResponseEntity<Object> addComment(Long userId, Long itemId, CommentDto commentDto) {
        return post(host + URL + "/" + itemId + "/comment", userId, commentDto);
    }


}
