package com.example.learn.service;

import com.example.learn.entity.Order;
import com.example.learn.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }
public Order addOrder(Order order){
        return  orderRepo.save(order);
}
public Order getOrderById(Long id){
return orderRepo.findById(id).orElseThrow(()->new RuntimeException("Order id not found"));
    }

public List<Order> searchItem(String keySearch){
        return orderRepo.findByContains(keySearch);
}

public Order updateOrder(Order updatedOrder,Long id){
        if(orderRepo.existsById(id)){
            updatedOrder.setId(id);
            return orderRepo.save(updatedOrder);
        }
        return null;
}
}
