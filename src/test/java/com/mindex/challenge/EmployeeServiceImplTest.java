package com.mindex.challenge;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.impl.EmployeeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        // Initialize a sample Employee object for testing
        employee = new Employee();
        employee.setEmployeeId("1");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");
        employee.setDepartment("Engineering");
    }

    @Test
    void testCreateEmployee() {
        // Mock the save method to return the employee object
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Call the create method
        Employee createdEmployee = employeeService.create(employee);

        // Verify the result
        assertNotNull(createdEmployee);
        assertEquals(employee.getEmployeeId(), createdEmployee.getEmployeeId());
        assertEquals(employee.getFirstName(), createdEmployee.getFirstName());
        assertEquals(employee.getLastName(), createdEmployee.getLastName());
        assertEquals(employee.getPosition(), createdEmployee.getPosition());
        assertEquals(employee.getDepartment(), createdEmployee.getDepartment());

        // Verify the save method was called once
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testReadEmployee() {
        // Mock the findById method to return the employee object
        when(employeeRepository.findById("1")).thenReturn(Optional.of(employee));

        // Call the read method
        Employee readEmployee = employeeService.read("1");

        // Verify the result
        assertNotNull(readEmployee);
        assertEquals(employee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEquals(employee.getFirstName(), readEmployee.getFirstName());
        assertEquals(employee.getLastName(), readEmployee.getLastName());
        assertEquals(employee.getPosition(), readEmployee.getPosition());
        assertEquals(employee.getDepartment(), readEmployee.getDepartment());

        // Verify the findById method was called once
        verify(employeeRepository, times(1)).findById("1");
    }

    @Test
    void testReadEmployee_NotFound() {
        // Mock the findById method to return an empty Optional
        when(employeeRepository.findById("1")).thenReturn(Optional.empty());

        // Call the read method and expect a RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeService.read("1");
        });

        // Verify the exception message
        assertEquals("Invalid employeeId: 1", exception.getMessage());

        // Verify the findById method was called once
        verify(employeeRepository, times(1)).findById("1");
    }

    @Test
    void testUpdateEmployee() {
        // Mock the save method to return the employee object
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Call the update method
        Employee updatedEmployee = employeeService.update(employee);

        // Verify the result
        assertNotNull(updatedEmployee);
        assertEquals(employee.getEmployeeId(), updatedEmployee.getEmployeeId());
        assertEquals(employee.getFirstName(), updatedEmployee.getFirstName());
        assertEquals(employee.getLastName(), updatedEmployee.getLastName());
        assertEquals(employee.getPosition(), updatedEmployee.getPosition());
        assertEquals(employee.getDepartment(), updatedEmployee.getDepartment());

        // Verify the save method was called once
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testListAllEmployees() {
        // Create a list with the sample employee
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        // Mock the findAll method to return the list of employees
        when(employeeRepository.findAll()).thenReturn(employees);

        // Call the listAll method
        List<Employee> allEmployees = employeeService.listAll();

        // Verify the result
        assertNotNull(allEmployees);
        assertEquals(1, allEmployees.size());
        assertEquals(employee.getEmployeeId(), allEmployees.get(0).getEmployeeId());

        // Verify the findAll method was called once
        verify(employeeRepository, times(1)).findAll();
    }
}
