package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT bk from Booking AS bk " +
            "WHERE bk.booker.id = ?1 ORDER BY bk.startDate desc")
    List<Booking> getAllUserBookings(Long userId);

    @Query("SELECT bk from Booking AS bk " +
            "WHERE bk.booker.id = ?1 AND UPPER(bk.status) LIKE ?2 " +
            "ORDER BY bk.startDate desc")
    List<Booking> getUserBookingByWaitingOrRejected(Long userId, String state);

    @Query("SELECT bk from Booking AS bk " +
            "WHERE bk.booker.id = ?1 AND UPPER(bk.status) LIKE 'APPROVED' AND bk.startDate <= ?2 AND bk.endDate >= ?2 " +
            "ORDER BY bk.startDate desc")
    List<Booking> getUserBookingByCurrent(Long userId, LocalDateTime now);

    @Query("SELECT bk from Booking AS bk " +
            "WHERE bk.booker.id = ?1 AND UPPER(bk.status) LIKE 'APPROVED' AND bk.endDate <= ?2 " +
            "ORDER BY bk.startDate desc")
    List<Booking> getUserBookingByPast(Long userId, LocalDateTime now);

    @Query("SELECT bk from Booking AS bk " +
            "WHERE bk.booker.id = ?1 AND UPPER(bk.status) LIKE 'APPROVED' AND bk.startDate >= ?2 " +
            "ORDER BY bk.startDate desc")
    List<Booking> getUserBookingByFuture(Long userId, LocalDateTime now);

    @Query("SELECT bk from Booking AS bk " +
            "JOIN bk.item AS it " +
            "WHERE it.user.id = ?1 " +
            "ORDER BY bk.startDate desc")
    List<Booking> getAllOwnerBooking(Long userId);

    @Query("SELECT bk from Booking AS bk " +
            "JOIN bk.item AS it " +
            "WHERE it.user.id = ?1 AND UPPER(bk.status) LIKE ?2 " +
            "ORDER BY bk.startDate desc")
    List<Booking> getOwnerBookingByWaitingOrRejected(Long userId, String state);

    @Query("SELECT bk from Booking AS bk " +
            "JOIN bk.item AS it " +
            "WHERE it.user.id = ?1 AND UPPER(bk.status) LIKE 'APPROVED' AND bk.startDate <= ?2 AND bk.endDate >= ?2 " +
            "ORDER BY bk.startDate desc")
    List<Booking> getOwnerBookingByCurrent(Long userId, LocalDateTime now);

    @Query("SELECT bk from Booking AS bk " +
            "JOIN bk.item AS it " +
            "WHERE it.user.id = ?1 AND UPPER(bk.status) LIKE 'APPROVED' AND bk.endDate <= ?2 " +
            "ORDER BY bk.startDate desc")
    List<Booking> getOwnerBookingByPast(Long userId, LocalDateTime now);

    @Query("SELECT bk from Booking AS bk " +
            "JOIN bk.item AS it " +
            "WHERE it.user.id = ?1 AND UPPER(bk.status) LIKE 'APPROVED' AND bk.startDate >= ?2 " +
            "ORDER BY bk.startDate desc")
    List<Booking> getOwnerBookingByFuture(Long userId, LocalDateTime now);

    @Query("SELECT COUNT(bk) FROM Booking AS bk " +
            "WHERE bk.booker.id = ?1 AND bk.item.id = ?2 AND bk.status LIKE 'APPROVED' AND bk.endDate <= ?3")
    Long countUserBookedItem(Long userId, Long itemId, LocalDateTime now);

}
