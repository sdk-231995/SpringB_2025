package com.abnamro.retailer.service;

import com.abnamro.retailer.entity.Order;
import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.dto.SaleAmountPerDay;
import com.abnamro.retailer.exception.InvalidInputException;
import com.abnamro.retailer.repository.OrderProductRepository;
import com.abnamro.retailer.util.ErrorConstants;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {

    private static final Integer LIMIT_TOP_ORDERED_PRODUCTS_TODAY = 5;

    private final OrderService orderService;

    private final OrderProductRepository orderProductRepository;

    public ReportService(OrderService orderService, OrderProductRepository orderProductRepository) {
        this.orderService = orderService;
        this.orderProductRepository = orderProductRepository;
    }

    public Order findOrderById(Long id) {
        return orderService.findById(id);
    }

    public List<Product> findTopSellingProductsOfToday() {
        return orderProductRepository.findTopOrderedProductsWithinDateTime(
                ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS),
                ZonedDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS),
                LIMIT_TOP_ORDERED_PRODUCTS_TODAY);
    }

    public List<Product> findLeastSellingProductsOfMonth() {
        return orderProductRepository.findLeastSellingProductsWithinDateTime(
                ZonedDateTime.now().withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS),
                ZonedDateTime.now().plusMonths(1).withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS),
                LIMIT_TOP_ORDERED_PRODUCTS_TODAY);
    }

    public List<SaleAmountPerDay> calculateSaleAmountWithinTime(Date fromDate, Date toDate) {
        if (fromDate.after(toDate)) {
            throw new InvalidInputException(ErrorConstants.ERROR_DATE_FROM_AFTER_TO);
        }

        final ZoneId id = ZoneId.systemDefault();
        return orderProductRepository.calculateSaleAmountWithinTime(ZonedDateTime.ofInstant(fromDate.toInstant(), id),
                ZonedDateTime.ofInstant(toDate.toInstant(), id).plusDays(1));
    }
}
