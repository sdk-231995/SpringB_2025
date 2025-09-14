package com.abnamro.retailer.resource;

import com.abnamro.retailer.entity.Order;
import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.dto.ReportProductDTO;
import com.abnamro.retailer.entity.dto.SaleAmountPerDay;
import com.abnamro.retailer.mapper.ProductMapper;
import com.abnamro.retailer.service.ReportService;
import static com.abnamro.retailer.util.SecurityUtils.ROLE_ADMIN;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Report resource.
 */
@RestController
@RequestMapping("/api/reports")
@Secured({ROLE_ADMIN})
public class ReportResource {

    private final ReportService reportService;

    /**
     * Instantiates a new Report resource.
     *
     * @param reportService the report service
     */
    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Find order by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("orders/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long id) {
        Order order = reportService.findOrderById(id);
        return ResponseEntity.ok(order);
    }

    /**
     * Find top selling products of today response entity.
     *
     * @return the response entity
     */
    @GetMapping("products/top-selling/today")
    public ResponseEntity<List<ReportProductDTO>> findTopSellingProductsOfToday() {
        List<Product> products = reportService.findTopSellingProductsOfToday();
        return ResponseEntity.ok(products.stream()
                .map(ProductMapper.INSTANCE::mapToReportProductDTO)
                .collect(Collectors.toList()));
    }

    /**
     * Find least selling products of month response entity.
     *
     * @return the response entity
     */
    @GetMapping("products/least-selling/month")
    public ResponseEntity<List<ReportProductDTO>> findLeastSellingProductsOfMonth() {
        List<Product> products = reportService.findLeastSellingProductsOfMonth();
        return ResponseEntity.ok(products.stream()
                .map(ProductMapper.INSTANCE::mapToReportProductDTO)
                .collect(Collectors.toList()));
    }

    /**
     * Calculate sale amount within time response entity.
     *
     * @param fromDate the from date
     * @param toDate   the to date
     * @return the response entity
     */
    @GetMapping("orders/count-sale")
    public ResponseEntity<List<SaleAmountPerDay>> calculateSaleAmountWithinTime(@RequestParam Date fromDate,
                                                                                @RequestParam Date toDate) {
        List<SaleAmountPerDay> totalAmount = reportService.calculateSaleAmountWithinTime(fromDate, toDate);
        return ResponseEntity.ok(totalAmount);
    }


}
