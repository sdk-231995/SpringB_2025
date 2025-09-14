For building and running the application:

Spring Boot 2.7.8,
Java 17,
Maven 3.6.0
Junit 5,
H2 database
Twilio/SMS library
are used.

A three-tier architecture is used to separate the logic of service/repository and controller layers to create a loosely-coupled
structure. Circuit breaker applied to have a second option on sending sms messages.(Currently only configured to send sms to my mobile phone)

You can run the application using Maven

```sh-session
$ mvn spring-boot:run
```

Go to http://localhost:8080/h2 and set JDBC url to `jdbc:h2:mem:retaildb;` press `Connect` to view the H2 console,
tables and data.

Go to http://localhost:8080/swagger-ui/index.html to view the endpoints and models.

Initial data of carts and items are loaded in `data.sql` file.

**Requests & Responses**

*Search products*
***

```sh-session
$ curl -X GET "http://localhost:8080/api/products/search/?name=bed" -H "accept: */*" -u user:userPassword
```

```json
[
  {
    "id": "1",
    "name": "bed",
    "price": "199.99",
    "quantityAvailable": "12"
  }
]
```

***
*Add product to cart*

```sh-session
$ curl -X POST "http://localhost:8080/api/carts/add" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"customerId\": 5, \"productId\": 1, \"quantity\": 3}" -u user:userPassword
```

```json
{
  "customerId": "5",
  "productId": "1",
  "quantity": "3"
}
```

***
*Place order, after adding product to cart*

```sh-session
$ curl -X POST "http://localhost:8080/api/orders/place" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"customerId\": 5, \"customerName\": \"john doe\", \"customerPhone\": \"+31123456\"}" -u user:userPassword
```

```json
4
```

*Search order with order id*
***

```sh-session
$ curl -X GET "http://localhost:8080/api/admin/search-order?orderId=4" -H "accept: */*" -u admin:adminPassword
```

```json
{
  "customerName": "john doe",
  "customerPhoneNumber": "+31123456",
  "totalAmount": 599.97,
  "orderItems": [
    {
      "orderId": 4,
      "productId": 1,
      "quantity": 3,
      "totalPrice": 599.97
    }
  ]
}
```

***
*Get Top 5 selling products of the day.*

```sh-session
$ curl -X GET "http://localhost:8080/api/admin/retrieve-best-selling-report" -H "accept: */*" -u admin:adminPassword
```

```json
[
  {
    "productName": "bed",
    "totalQuantitySold": "3",
    "quantityAvailable": "9",
    "price": "199.99",
    "isNearingOutOfStock": true
  }
]
```

***
After making some orders,
*Get least selling products of the month*

```sh-session
$ curl -X GET "http://localhost:8080/api/admin/retrieve-least-selling-report" -H "accept: */*" -u admin:adminPassword
```

```json
[
  {
    "productName": "bed",
    "totalQuantitySold": "1",
    "quantityAvailable": "11",
    "price": "199.99",
    "isNearingOutOfStock": false
  },
  {
    "productName": "desk",
    "totalQuantitySold": "3",
    "quantityAvailable": "9",
    "price": "250.0",
    "isNearingOutOfStock": true
  }
]
```

***
*Sale amount per day for custom dates range*

```sh-session
$ curl -X GET "http://localhost:8080/api/admin/retrieve-sale-amount-report?endDate=2023-01-15&startDate=2023-01-09" -H "accept: */*" -u admin:adminPassword
```

```json
[
  {
    "date": "2023-01-11 00:00:00.0",
    "totalAmount": "699.39"
  },
  {
    "date": "2023-01-12 00:00:00.0",
    "totalAmount": "449.99"
  },
  {
    "date": "2023-01-13 00:00:00.0",
    "totalAmount": "989.98"
  }
]
```

-------------------------------------------------------------------------------------------------------------------------