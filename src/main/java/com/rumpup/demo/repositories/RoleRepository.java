package com.rumpup.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rumpup.demo.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{
}
