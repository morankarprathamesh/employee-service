package com.example.employeeservice.controller;

import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.employeeservice.dto.EmployeeRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;


import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    // Employee search feature - development in progress
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Create employee",
            description = "Creates a new employee and saves the employee details"
    )
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse savedEmployee =
                employeeService.createEmployee(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedEmployee);
    }
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Operation(
            summary = "Get employee by ID",
            description = "Fetches an employee using their unique ID"
    )
    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }


    @Operation(
            summary = "Update employee",
            description = "Updates an existing employee using their ID"
    )
    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return employeeService.updateEmployee(id, request);
    }


    @Operation(
            summary = "Delete employee",
            description = "Deletes an employee using their ID"
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }


}