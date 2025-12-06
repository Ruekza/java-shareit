package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUser_Id(Long userId);

    @Query("SELECT it FROM Item AS it" +
            " WHERE it.available = true AND (LOWER(it.name) LIKE %?1% OR LOWER(it.description) LIKE %?1%)")
    List<Item> findByNameOrDescription(String text);


    @Query("SELECT bk.endDate FROM Booking AS bk " +
            "WHERE bk.item.id = ?1 AND bk.endDate < ?2 AND bk.status LIKE 'APPROVED' " +
            "ORDER BY bk.endDate DESC " +
            "LIMIT 1")
    LocalDateTime findLastBookingForItem(Long itemId, LocalDateTime now);

    @Query("SELECT bk.startDate FROM Booking  AS bk " +
            "WHERE bk.item.id = ?1 AND bk.startDate > ?2 " +
            "ORDER BY bk.startDate ASC " +
            "LIMIT 1")
    LocalDateTime findNextBookingForItem(Long itemId, LocalDateTime now);

}
