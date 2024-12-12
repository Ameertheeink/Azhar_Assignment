package com.example.learn.controller;

import com.example.learn.entity.Order;
import com.example.learn.entity.OrderResponse;
import com.example.learn.entity.Payment;
import com.example.learn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/allorders")
    public ResponseEntity<List<Order>> getAllOrder() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody Order order) {
        Order addOrder = orderService.addOrder(order);


        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(addOrder.getId());


        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("/search/{key}")
    public ResponseEntity<List<Order>> searchOrderByKey(@PathVariable String key) {
        List<Order> order = orderService.searchItem(key);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("/pay")
    public ResponseEntity<Payment> getPaymentInfo() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity<String> response = restTemplate.exchange("https://httpbin.org/", HttpMethod.GET, entity, String.class);
        System.out.println(response.getStatusCode());
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.status(201).body(new Payment());
        } else {
            return ResponseEntity.status(503).build();
        }

    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable Long id) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity("https://httpbin.org/", null, String.class);
       /*  if(response.getStatusCode()==HttpStatus.OK){
             return ResponseEntity.status(HttpStatus.CREATED).body("Payment Successful");
         }
         else{
             return ResponseEntity.status((HttpStatus.SERVICE_UNAVAILABLE)).body("Payment Failed");
         }*/
            return ResponseEntity.status(HttpStatus.CREATED).body("Payment Successful");
        } catch (Exception e) {
            return ResponseEntity.status((HttpStatus.SERVICE_UNAVAILABLE)).body("Payment Failed");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody Order order, @PathVariable Long id) {

        Order newOrder = orderService.updateOrder(order, id);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(newOrder.getId());


        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }
}