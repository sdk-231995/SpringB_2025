package com.xyz.retail.services;

import com.xyz.retail.dtos.ReportResponse;
import com.xyz.retail.dtos.SaleAmountReportResponse;
import com.xyz.retail.entities.OrderEntity;
import com.xyz.retail.entities.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    public OrderEntity getOrderById(final Long id) {
        return orderService.getOrderById(id);
    }

    public List<ReportResponse> retrieveTopFiveProductsOfTheDay() {
        final List<ReportResponse> result = new ArrayList<>();
        final List<Object[]> productsOfDayArray = orderItemService.retrieveTopFiveProductsOfTheDay();
        productsOfDayArray.forEach(report -> populateReportResponse(report, result));
        return result;
    }

    public List<ReportResponse> retrieveLeastSellingProductsOfTheMonth() {
        final List<ReportResponse> result = new ArrayList<>();
        final List<Object[]> productsOfDayArray = orderItemService.retrieveLeastSellingProductsOfTheMonth();
        productsOfDayArray.forEach(report -> populateReportResponse(report, result));
        return result;
    }

    public List<SaleAmountReportResponse> retrieveSalesAmountForRange(final LocalDate startDate, final LocalDate endDate) {
        final List<SaleAmountReportResponse> result = new ArrayList<>();
        final List<Object[]> productsOfDayArray = orderService.getSaleAmountForDates(startDate, endDate);
        productsOfDayArray.forEach(report -> populateSaleReportResponse(result, report));
        return result;
    }

    private void populateSaleReportResponse(List<SaleAmountReportResponse> result, Object[] report) {
        result.add(SaleAmountReportResponse.builder().date(report[0].toString()).totalAmount(report[1].toString()).build());
    }

    private void populateReportResponse(Object[] report, List<ReportResponse> result) {
        final ProductEntity product = productService.getProductById(Long.parseLong(report[0].toString()));
        result.add(ReportResponse.builder().productName(product.getName())
                .totalQuantitySold(report[1].toString())
                .price(String.valueOf(product.getPrice()))
                .quantityAvailable(String.valueOf(product.getQuantityAvailable()))
                .isNearingOutOfStock(product.getQuantityAvailable() < 10)
                .build());
    }
}
