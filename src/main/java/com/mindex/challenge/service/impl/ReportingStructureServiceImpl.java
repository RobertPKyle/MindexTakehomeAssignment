package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Stack;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        // Retrieve the employee with the given employeeId from the repository
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        
        // If the employee is not found, throw a RuntimeException
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
        
        // Calculate the number of reports for the employee
        int numberOfReports = calculateNumberOfReports(employee);
        
        // Return a new ReportingStructure object containing the employee and the number of reports
        return new ReportingStructure(employee, numberOfReports);
    }

    @Override
    public void logReportingStructure(String employeeId) {
        // Retrieve the employee with the given employeeId from the repository
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        
        // If the employee is not found, throw a RuntimeException
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
        
        // Log the reporting structure for the employee with the given employeeId
        LOG.info("Reporting structure for employeeId [{}]:", employeeId);
        
        // Log the details of the employee and their reporting structure
        logEmployeeDetails(employee, 0);
    }

    private int calculateNumberOfReports(Employee employee) {
        // If the employee has no direct reports, return 0
        if (employee.getDirectReports() == null) {
            return 0;
        }
        
        // Initialize the number of reports with the size of the directReports list
        int numberOfReports = employee.getDirectReports().size();
        
        // Iterate through each direct report
        for (Employee report : employee.getDirectReports()) {
            // Retrieve the full details of the direct report from the repository
            Employee fullReport = employeeRepository.findById(report.getEmployeeId()).orElse(null);
            
            // If the direct report is found, recursively calculate their number of reports
            if (fullReport != null) {
                numberOfReports += calculateNumberOfReports(fullReport);
            }
        }
        
        // Return the total number of reports
        return numberOfReports;
    }

    private void logEmployeeDetails(Employee employee, int level) {
        // If the employee is null, return immediately
        if (employee == null) {
            return;
        }
        
        // Create an indentation string based on the level of the employee in the hierarchy
        String indent = " ".repeat(level * 4);
        
        // Log the details of the employee with the appropriate indentation
        LOG.info("{}Employee ID: {}, Name: {} {}", indent, employee.getEmployeeId(), employee.getFirstName(), employee.getLastName());
        
        // If the employee has direct reports, log their details as well
        if (employee.getDirectReports() != null) {
            for (Employee directReport : employee.getDirectReports()) {
                // Retrieve the full details of the direct report from the repository
                Employee fullReport = employeeRepository.findById(directReport.getEmployeeId()).orElse(null);
                
                // Recursively log the details of the direct report with increased indentation level
                logEmployeeDetails(fullReport, level + 1);
            }
        }
    }
}