package com.rumpup.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rumpup.demo.entities.Order;
import com.rumpup.demo.entities.User;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{

}
