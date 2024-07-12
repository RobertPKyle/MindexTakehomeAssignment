package com.mindex.challenge;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.impl.CompensationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompensationServiceImplTest {

    @Mock
    private CompensationRepository compensationRepository;

    @Mock
    private EmployeeRepository employeeRepository; 

    @InjectMocks
    private CompensationServiceImpl compensationService; 
    private Employee employee;
    private Compensation compensation;

    @BeforeEach
    void setUp() {
        // Initialize a sample Employee object for testing
        employee = new Employee();
        employee.setEmployeeId("1");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");
        employee.setDepartment("Engineering");

        // Initialize a sample Compensation object for testing
        compensation = new Compensation();
        compensation.setEmployee(employee);
        compensation.setSalary(100000);
        compensation.setEffectiveDate(new Date());
    }

    @Test
    void testCreateCompensation() {
        // Mock the save method to return the compensation object
        when(compensationRepository.save(any(Compensation.class))).thenReturn(compensation);

        // Call the create method
        Compensation createdCompensation = compensationService.create(compensation);

        // Verify the result
        assertNotNull(createdCompensation);
        assertEquals(compensation.getEmployee(), createdCompensation.getEmployee());
        assertEquals(compensation.getSalary(), createdCompensation.getSalary());
        assertEquals(compensation.getEffectiveDate(), createdCompensation.getEffectiveDate());

        // Verify the save method was called once
        verify(compensationRepository, times(1)).save(compensation);
    }

    @Test
    void testReadCompensation() {
        // Mock the findById method to return the employee object
        when(employeeRepository.findById("1")).thenReturn(Optional.of(employee));
        // Mock the findByEmployee method to return the compensation object
        when(compensationRepository.findByEmployee(employee)).thenReturn(compensation);

        // Call the read method
        Compensation readCompensation = compensationService.read("1");

        // Verify the result
        assertNotNull(readCompensation);
        assertEquals(compensation.getEmployee(), readCompensation.getEmployee());
        assertEquals(compensation.getSalary(), readCompensation.getSalary());
        assertEquals(compensation.getEffectiveDate(), readCompensation.getEffectiveDate());

        // Verify the findById and findByEmployee methods were called once
        verify(employeeRepository, times(1)).findById("1");
        verify(compensationRepository, times(1)).findByEmployee(employee);
    }

    @Test
    void testReadCompensation_EmployeeNotFound() {
        // Mock the findById method to return an empty Optional
        when(employeeRepository.findById("1")).thenReturn(Optional.empty());

        // Call the read method and expect a RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () -> {
            compensationService.read("1");
        });

        // Verify the exception message
        assertEquals("Invalid employeeId: 1", exception.getMessage());

        // Verify the findById method was called once
        verify(employeeRepository, times(1)).findById("1");
        // Verify the findByEmployee method was not called
        verify(compensationRepository, times(0)).findByEmployee(any(Employee.class));
    }

    @Test
    void testUpdateCompensation() {
        // Mock the findById method to return the employee object
        when(employeeRepository.findById("1")).thenReturn(Optional.of(employee));
        // Mock the save method to return the compensation object
        when(compensationRepository.save(any(Compensation.class))).thenReturn(compensation);

        // Call the update method
        Compensation updatedCompensation = compensationService.update("1", compensation);

        // Verify the result
        assertNotNull(updatedCompensation);
        assertEquals(compensation.getEmployee(), updatedCompensation.getEmployee());
        assertEquals(compensation.getSalary(), updatedCompensation.getSalary());
        assertEquals(compensation.getEffectiveDate(), updatedCompensation.getEffectiveDate());

        // Verify the findById and save methods were called once
        verify(employeeRepository, times(1)).findById("1");
        verify(compensationRepository, times(1)).save(compensation);
    }

    @Test
    void testUpdateCompensation_EmployeeNotFound() {
        // Mock the findById method to return an empty Optional
        when(employeeRepository.findById("1")).thenReturn(Optional.empty());

        // Call the update method and expect a RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () -> {
            compensationService.update("1", compensation);
        });

        // Verify the exception message
        assertEquals("Invalid employeeId: 1", exception.getMessage());

        // Verify the findById method was called once
        verify(employeeRepository, times(1)).findById("1");
        // Verify the save method was not called
        verify(compensationRepository, times(0)).save(any(Compensation.class));
    }
}