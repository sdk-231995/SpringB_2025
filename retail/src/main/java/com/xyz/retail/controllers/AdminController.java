package com.xyz.retail.controllers;

import com.xyz.retail.dtos.DtoConverter;
import com.xyz.retail.dtos.OrderResponse;
import com.xyz.retail.dtos.ReportResponse;
import com.xyz.retail.dtos.SaleAmountReportResponse;
import com.xyz.retail.entities.OrderEntity;
import com.xyz.retail.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final DtoConverter dtoConverter;

    @GetMapping("/search-order")
    public ResponseEntity<OrderResponse> searchOrder(@RequestParam long orderId) {
        final OrderEntity orderEntity = adminService.getOrderById(orderId);
        final OrderResponse orderResponse = dtoConverter.convertOrderEntityToOrderResponse(orderEntity);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/retrieve-best-selling-report")
    public ResponseEntity<List<ReportResponse>> retrieveBestSellingReport() {
        final List<ReportResponse> reportResponses = adminService.retrieveTopFiveProductsOfTheDay();
        return new ResponseEntity<>(reportResponses, HttpStatus.OK);
    }

    @GetMapping("/retrieve-least-selling-report")
    public ResponseEntity<List<ReportResponse>> retrieveLeastSellingReport() {
        final List<ReportResponse> reportResponses = adminService.retrieveLeastSellingProductsOfTheMonth();
        return new ResponseEntity<>(reportResponses, HttpStatus.OK);
    }

    @GetMapping("/retrieve-sale-amount-report")
    public ResponseEntity<List<SaleAmountReportResponse>> retrieveSaleAmount(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate startDate
            , @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate endDate) {
        final List<SaleAmountReportResponse> saleAmountReportResponses = adminService.retrieveSalesAmountForRange(startDate, endDate);
        return new ResponseEntity<>(saleAmountReportResponses, HttpStatus.OK);
    }
}
