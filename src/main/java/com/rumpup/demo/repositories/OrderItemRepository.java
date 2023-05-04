package com.rumpup.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rumpup.demo.entities.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer>{
}
