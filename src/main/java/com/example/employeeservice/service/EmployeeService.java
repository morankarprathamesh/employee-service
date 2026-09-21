package com.example.employeeservice.service;

import com.example.employeeservice.dto.EmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import com.example.employeeservice.exception.EmployeeNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class EmployeeService {

    private static final Logger log =
            LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {

        log.info("Creating employee with email={}", request.getEmail());

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        Employee savedEmployee = employeeRepository.save(employee);

        return new EmployeeResponse(
                savedEmployee.getId(),
                savedEmployee.getName(),
                savedEmployee.getEmail(),
                savedEmployee.getDepartment(),
                savedEmployee.getSalary()
        );
    }


        public List<Employee> getAllEmployees() {
            log.info("Fetching all employees");
            return employeeRepository.findAll();
        }


    public EmployeeResponse getEmployeeById(Long id) {

        log.info("Fetching employee with id={}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        existingEmployee.setName(request.getName());
        existingEmployee.setEmail(request.getEmail());
        existingEmployee.setDepartment(request.getDepartment());
        existingEmployee.setSalary(request.getSalary());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return new EmployeeResponse(
                updatedEmployee.getId(),
                updatedEmployee.getName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getDepartment(),
                updatedEmployee.getSalary()
        );
    }
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id={}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        employeeRepository.delete(employee);
    }

}