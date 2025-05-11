package com.edacy.edacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edacy.edacy.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Custom query methods can be defined here if needed
    // For example, findByName(String name) or findByEmail(String email)

}
