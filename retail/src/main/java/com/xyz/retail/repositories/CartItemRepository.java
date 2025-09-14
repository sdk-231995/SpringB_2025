package com.xyz.retail.repositories;

import com.xyz.retail.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    void deleteByCustomerId(final long customerId);

    List<CartItemEntity> findByCustomerId(final long customerId);
}
