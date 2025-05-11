package com.edacy.edacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edacy.edacy.entities.Employee;
import com.edacy.edacy.services.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(maxAge = 3600)
@RestController
public class EmployeeController {
    // This class will handle HTTP requests related to employees
    // It will use the EmployeeService to perform CRUD operations
    @Autowired
    private EmployeeService employeeService;

    // Example endpoints can be defined here, such as GET, POST, PUT, DELETE
    // For example:
    @GetMapping("/employees")
    @Operation(summary = "Get all employees", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees); // Ne pas oublier de retourner les données
    }

    // Example endpoint to save a new employee
    @GetMapping("/employees/{id}")
    @Operation(summary = "Get employee by ID", security = @SecurityRequirement(name = "bearerAuth"))
    public Employee getEmployeeById(Long id) {
        return employeeService.findEmployeeById(id);
    }

    // Example endpoint to delete an employee by ID
    @DeleteMapping("/employees/{id}")
    @Operation(summary = "Delete employee by ID", security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteEmployeeById(Long id) {
        employeeService.deleteEmployeeById(id);
    }

    // Example endpoint to update an employee
    @PutMapping("/employees/{id}")
    @Operation(summary = "Update employee by ID", security = @SecurityRequirement(name = "bearerAuth"))
    public Employee updateEmployee(Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    // Example endpoint to save a new employee
    @PostMapping("/employees")
    @Operation(summary = "Save employee", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(saved); // ou status CREATED
    }

}
