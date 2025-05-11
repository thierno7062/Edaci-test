package com.edacy.edacy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edacy.edacy.entities.Role;
import com.edacy.edacy.enums.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);  // Changé de String à ERole
     @Query("SELECT r FROM Role r WHERE UPPER(r.name) = UPPER(?1)")
    Optional<Role> findByNameCaseInsensitive(String roleName);



}
