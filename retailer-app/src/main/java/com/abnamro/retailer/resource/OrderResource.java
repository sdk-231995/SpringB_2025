package com.abnamro.retailer.resource;

import com.abnamro.retailer.entity.Order;
import com.abnamro.retailer.entity.dto.OrderDTO;
import com.abnamro.retailer.service.OrderService;
import static com.abnamro.retailer.util.SecurityUtils.ROLE_ADMIN;
import static com.abnamro.retailer.util.SecurityUtils.ROLE_USER;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Order resource.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderResource {

    private final OrderService orderService;

    /**
     * Instantiates a new Order resource.
     *
     * @param orderService the order service
     */
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping
    @Secured({ROLE_ADMIN})
    @ApiOperation(value = "Get all Orders", notes = "Returns all saved Orders.\n\n" +
            "Available for admins only")
    public ResponseEntity<List<Order>> getAll() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    /**
     * Gets one by id.
     *
     * @param id the id
     * @return the one by id
     */
    @GetMapping("{id}")
    @Secured({ROLE_ADMIN})
    @ApiOperation(value = "Get one Order", notes = "Returns one saved Order.\n\n" +
            "Available for admins only")
    public ResponseEntity<Order> getOneById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    /**
     * Create response entity.
     *
     * @param orderDTO the order dto
     * @return the response entity
     */
    @PostMapping
    @Secured({ROLE_ADMIN, ROLE_USER})
    @ApiOperation(value = "Create Order", notes = "Returns the created Order.\n\n" +
            "Available for all users.\n\n" +
            "Sample of correct Mobile `+31616786789`")
    public ResponseEntity<Order> create(@RequestBody @Valid OrderDTO orderDTO) {
        Order savedOrder = orderService.save(orderDTO);
        return ResponseEntity.ok(savedOrder);
    }

}
