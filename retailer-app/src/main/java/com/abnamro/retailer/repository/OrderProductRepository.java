package com.abnamro.retailer.repository;

import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.dto.SaleAmountPerDay;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OrderProductRepository {


    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> findTopOrderedProductsWithinDateTime(ZonedDateTime fromDate, ZonedDateTime toDate, Integer limit) {
        return entityManager.createQuery("SELECT p " +
                        "FROM Order o JOIN OrderProduct op ON o.id = op.order.id " +
                        "JOIN Product p ON op.product.id = p.id " +
                        "WHERE o.time >= ?1 AND o.time < ?2 " +
                        "GROUP BY p.id " +
                        "ORDER BY SUM(op.quantity) DESC", Product.class)
                .setParameter(1, fromDate)
                .setParameter(2, toDate)
                .setMaxResults(limit).getResultList();
    }

    public List<Product> findLeastSellingProductsWithinDateTime(ZonedDateTime fromDate, ZonedDateTime toDate, Integer limit) {
        return entityManager.createNativeQuery("SELECT p.* " +
                        "FROM product p " +
                        "LEFT JOIN (SELECT op.product_id, sum(op.quantity) total " +
                        "FROM order_product op " +
                        "INNER JOIN app_order o on op.order_id = o.id " +
                        "WHERE o.time >= ? " +
                        "AND o.time < ? " +
                        "GROUP BY op.product_id) as c ON p.id = c.product_id " +
                        "ORDER BY c.total", Product.class)
                .setParameter(1, fromDate)
                .setParameter(2, toDate)
                .setMaxResults(limit).getResultList();
    }

    public List<SaleAmountPerDay> calculateSaleAmountWithinTime(ZonedDateTime fromDate, ZonedDateTime toDate) {
        return entityManager.createQuery(
                "SELECT NEW com.abnamro.retailer.entity.SaleAmountPerDay(year(time), month(time),day(time), sum(totalPrice)) " +
                        "FROM Order " +
                        "WHERE time >= ?1 and time < ?2 " +
                        "GROUP BY  year(time), month(time), day(time)", SaleAmountPerDay.class)
                .setParameter(1, fromDate)
                .setParameter(2, toDate)
                .getResultList();
    }
}
