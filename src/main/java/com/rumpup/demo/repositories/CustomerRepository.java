package com.rumpup.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rumpup.demo.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>{

}
