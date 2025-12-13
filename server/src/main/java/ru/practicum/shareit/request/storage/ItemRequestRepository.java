package ru.practicum.shareit.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    @Query("SELECT rq from ItemRequest  AS rq " +
            "WHERE rq.requestor.id = ?1 " +
            "ORDER BY rq.created desc")
    List<ItemRequest> getUserRequests(Long userId);

    @Query("SELECT rq from ItemRequest  AS rq " +
            "WHERE rq.requestor.id <> ?1 " +
            "ORDER BY rq.created desc")
    List<ItemRequest> getAllRequest(Long userId);
}
