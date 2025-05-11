package com.edacy.edacy.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edacy.edacy.entities.Employee;
import com.edacy.edacy.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {

    // This class will contain the business logic for managing employees
    // It will interact with the EmployeeRepository to perform CRUD operations
    private EmployeeRepository employeeRepository;

    // Example method to get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Example method to save a new employee
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }


    // Example method to find an employee by ID
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
    // Example method to delete an employee by ID
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
    // Example method to update an employee
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
   
   
}
