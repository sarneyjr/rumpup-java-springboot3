package com.rumpup.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rumpup.demo.entities.Role;
import com.rumpup.demo.entities.enums.Authorities;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r WHERE r.authority = :authority")
    Role findByAuthority(@Param("authority") Authorities authority);
}
