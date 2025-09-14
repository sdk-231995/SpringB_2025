package com.xyz.retail.repositories;

import com.xyz.retail.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    @Query(value = "SELECT product_id, sum(quantity) as total_quantity FROM order_items WHERE TRUNC(created_date) = CURRENT_DATE() GROUP BY product_id ORDER BY total_quantity DESC LIMIT 5", nativeQuery = true)
    List<Object[]> retrieveTop5SellingProductsOfDay();

    @Query(value = "SELECT product_id, sum(quantity) as total_quantity FROM order_items WHERE MONTH(TRUNC(created_date)) = MONTH(CURRENT_DATE()) GROUP BY product_id ORDER BY total_quantity ASC LIMIT 5", nativeQuery = true)
    List<Object[]> retrieveLeastSellingProductsOfTheMonth();
}
