package com.mycompany.bookingservice.repository;

import com.mycompany.bookingservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
