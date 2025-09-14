package com.abnamro.retailer.config;

import com.abnamro.retailer.entity.Order;
import com.abnamro.retailer.entity.Product;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SecurityConfigTest {
    TestRestTemplate userRestTemplate;
    TestRestTemplate adminRestTemplate;
    URL base;
    @LocalServerPort
    int port;

    @Before
    public void setUp() throws MalformedURLException {
        userRestTemplate = new TestRestTemplate("user", "password");
        adminRestTemplate = new TestRestTemplate("admin", "admin");
        base = new URL("http://localhost:" + port);
    }

    @Test
    public void whenLoggedUserRequestsProducts_ThenSuccess() {
        ResponseEntity<Product> response = userRestTemplate
                .getForEntity(base.toString() + "/api/products/1", Product.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenLoggedUserRequestsReports_ThenForbidden() {
        ResponseEntity<String> response = userRestTemplate
                .getForEntity(base.toString() + "/api/reports/orders/1", String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void whenLoggedAdminRequestsReports_ThenSuccess() {
        ResponseEntity<List> response = adminRestTemplate
                .getForEntity(base.toString() + "/api/reports/products/least-selling/month", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenLoggedUserRequestsTopSellingReports_ThenForbidden() {
        ResponseEntity<String> response = userRestTemplate
                .getForEntity(base.toString() + "/api/reports/products/top-selling/today", String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void whenLoggedAdminRequestsOrders_ThenSuccess() {
        ResponseEntity<Order> response = adminRestTemplate.getForEntity(base.toString() + "/api/orders/1", Order.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage() {
        userRestTemplate = new TestRestTemplate("user", "wrongpassword");
        ResponseEntity<String> response = userRestTemplate.getForEntity(base.toString(), String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

}