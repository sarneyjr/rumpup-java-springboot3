package com.rumpup.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rumpup.demo.entities.ProductOffering;

@Repository
public interface ProductOfferingRepository extends JpaRepository<ProductOffering,Integer>{
}
