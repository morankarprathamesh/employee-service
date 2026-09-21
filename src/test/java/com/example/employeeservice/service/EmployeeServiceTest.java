package com.example.employeeservice.service;

import com.example.employeeservice.dto.EmployeeRequest;
import com.example.employeeservice.repository.EmployeeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.entity.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.employeeservice.exception.EmployeeNotFoundException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldGetEmployeeById() {

        Employee employee = new Employee();

        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setDepartment("IT");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeResponse response =
                employeeService.getEmployeeById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Rahul", response.getName());
        assertEquals("rahul@gmail.com", response.getEmail());
        assertEquals("IT", response.getDepartment());
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(1L)
        );
    }

        @Test
        void shouldCreateEmployee() {

            EmployeeRequest request = new EmployeeRequest();

            request.setName("Amit");
            request.setEmail("amit@gmail.com");
            request.setDepartment("IT");
            request.setSalary(50000.0);

            Employee savedEmployee = new Employee();

            savedEmployee.setId(2L);
            savedEmployee.setName("Amit");
            savedEmployee.setEmail("amit@gmail.com");
            savedEmployee.setDepartment("IT");
            savedEmployee.setSalary(50000.0);

            when(employeeRepository.save(any(Employee.class)))
                    .thenReturn(savedEmployee);

            EmployeeResponse response =
                    employeeService.createEmployee(request);

            assertEquals(2L, response.getId());
            assertEquals("Amit", response.getName());
            assertEquals("amit@gmail.com", response.getEmail());
            assertEquals("IT", response.getDepartment());
            assertEquals(50000.0, response.getSalary());
        }

    @Test
    void shouldGetAllEmployees() {

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Rahul");
        employee1.setEmail("rahul@gmail.com");
        employee1.setDepartment("IT");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Amit");
        employee2.setEmail("amit@gmail.com");
        employee2.setDepartment("HR");

        when(employeeRepository.findAll())
                .thenReturn(List.of(employee1, employee2));

        List<Employee> employees =
                employeeService.getAllEmployees();

        assertEquals(2, employees.size());
        assertEquals("Rahul", employees.get(0).getName());
        assertEquals("Amit", employees.get(1).getName());
    }

    @Test
    void shouldUpdateEmployee() {

        EmployeeRequest request = new EmployeeRequest();

        request.setName("Rahul Updated");
        request.setEmail("rahul.updated@gmail.com");
        request.setDepartment("Finance");
        request.setSalary(60000.0);

        Employee existingEmployee = new Employee();

        existingEmployee.setId(1L);
        existingEmployee.setName("Rahul");
        existingEmployee.setEmail("rahul@gmail.com");
        existingEmployee.setDepartment("IT");
        existingEmployee.setSalary(50000.0);

        Employee updatedEmployee = new Employee();

        updatedEmployee.setId(1L);
        updatedEmployee.setName("Rahul Updated");
        updatedEmployee.setEmail("rahul.updated@gmail.com");
        updatedEmployee.setDepartment("Finance");
        updatedEmployee.setSalary(60000.0);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.save(existingEmployee))
                .thenReturn(updatedEmployee);

        EmployeeResponse response =
                employeeService.updateEmployee(1L, request);

        assertEquals(1L, response.getId());
        assertEquals("Rahul Updated", response.getName());
        assertEquals("rahul.updated@gmail.com", response.getEmail());
        assertEquals("Finance", response.getDepartment());
        assertEquals(60000.0, response.getSalary());
    }



    @Test
    void shouldDeleteEmployee() {

        Employee employee = new Employee();

        employee.setId(1L);
        employee.setName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setDepartment("IT");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).delete(employee);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingEmployee() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.deleteEmployee(1L)
        );
    }
}