package com.rumpup.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rumpup.demo.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
}
