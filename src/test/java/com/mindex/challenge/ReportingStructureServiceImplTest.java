package com.mindex.challenge;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.impl.ReportingStructureServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Extends JUnit 5 with Mockito
public class ReportingStructureServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository; // Mocked EmployeeRepository

    @InjectMocks
    private ReportingStructureServiceImpl reportingStructureService; // Inject mocks into ReportingStructureServiceImpl

    private Employee employee;
    private Employee report1;
    private Employee report2;

    @BeforeEach
    void setUp() {
        // Initialize a sample Employee object for testing
        employee = new Employee();
        employee.setEmployeeId("1");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");
        employee.setDepartment("Engineering");

        // Initialize two direct reports for the employee
        report1 = new Employee();
        report1.setEmployeeId("2");

        report2 = new Employee();
        report2.setEmployeeId("3");

        // Set direct reports for the main employee
        employee.setDirectReports(Arrays.asList(report1, report2));
    }

    @Test
    void testGetReportingStructure() {
        // Mock the findById method to return the employee and direct reports
        when(employeeRepository.findById("1")).thenReturn(Optional.of(employee));
        when(employeeRepository.findById("2")).thenReturn(Optional.of(report1));
        when(employeeRepository.findById("3")).thenReturn(Optional.of(report2));

        // Call the getReportingStructure method
        ReportingStructure reportingStructure = reportingStructureService.getReportingStructure("1");

        // Verify the result
        assertNotNull(reportingStructure);
        assertEquals(2, reportingStructure.getNumberOfReports()); // Verify the number of direct reports
    }

    @Test
    void testGetReportingStructure_EmployeeNotFound() {
        // Mock the findById method to return an empty Optional
        when(employeeRepository.findById("1")).thenReturn(Optional.empty());

        // Call the getReportingStructure method and expect a RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reportingStructureService.getReportingStructure("1");
        });

        // Verify the exception message
        assertEquals("Invalid employeeId: 1", exception.getMessage());
    }


    @Test
    void testLogReportingStructure_EmployeeNotFound() {
        // Mock the findById method to return an empty Optional
        when(employeeRepository.findById("1")).thenReturn(Optional.empty());

        // Call the logReportingStructure method and expect a RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reportingStructureService.logReportingStructure("1");
        });

        // Verify the exception message
        assertEquals("Invalid employeeId: 1", exception.getMessage());
    }
}