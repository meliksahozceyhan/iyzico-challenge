package com.iyzico.challenge.repository;

import com.iyzico.challenge.data.view.SeatListView;
import com.iyzico.challenge.data.view.SeatView;
import com.iyzico.challenge.entity.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {


    Optional<SeatView> getSeatById(Long id);

    Page<SeatListView> findAllByFlightId(Long id, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM seat s WHERE s.id = :id")
    Optional<Seat> findSeatByIdForPurchase(Long id);
}
