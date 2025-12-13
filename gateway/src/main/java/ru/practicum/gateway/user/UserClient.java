package ru.practicum.gateway.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.gateway.client.BaseClient;
import ru.practicum.gateway.user.dto.UserDto;

@Service
public class UserClient extends BaseClient {
    private static final String URL = "/users";

    @Value("${server.host}")
    private String host;

//    public UserClient() {
//        super(new RestTemplate());
//    }

    @Autowired
    public UserClient(@Value("${server.host}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + URL))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createUser(UserDto userDto) {
        return post(host + URL, userDto);
    }

    public ResponseEntity<Object> updateUser(Long userId, UserDto userDto) {
        return patch(host + URL + "/" + userId, userDto);
    }

    public ResponseEntity<Object> deleteUser(Long userId) {
        return delete(host + URL + "/" + userId);
    }

    public ResponseEntity<Object> getUser(Long userId) {
        return get(host + URL + "/" + userId);
    }

    public ResponseEntity<Object> getAllUsers() {
        return get(host + URL);
    }

}
