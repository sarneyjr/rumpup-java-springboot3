package com.rumpup.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rumpup.demo.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{

}
