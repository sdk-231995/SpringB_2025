package com.xyz.retail.repositories;

import com.xyz.retail.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "SELECT TRUNC(created_date), SUM(total_amount) FROM orders WHERE TRUNC(created_date) >= :startDate AND TRUNC(created_date) <= :endDate GROUP BY TRUNC(created_date) ORDER BY TRUNC(created_date) ASC LIMIT 5", nativeQuery = true)
    List<Object[]> retrieveSaleAmountForDates(@Param(value = "startDate") final LocalDate startDate, @Param(value = "endDate") final LocalDate endDate);
}
