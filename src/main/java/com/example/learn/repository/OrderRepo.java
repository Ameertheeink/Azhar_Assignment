package com.example.learn.repository;

import com.example.learn.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {

//    @Query(value="Select * from orders o where order_item like %:valueToCheck%",nativeQuery = true)
//    List<Order> findByContains(@Param("valueToCheck") String valueToCheck);

    @Query("select e from Order e where e.order_item  like %:valueToCheck%")
    List<Order> findByContains(@Param("valueToCheck") String valueToCheck);
}