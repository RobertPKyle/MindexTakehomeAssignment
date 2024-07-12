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
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
        int numberOfReports = calculateNumberOfReports(employee);
        return new ReportingStructure(employee, numberOfReports);
    }

    @Override
    public void logReportingStructure(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
        LOG.info("Reporting structure for employeeId [{}]:", employeeId);
        logEmployeeDetails(employee, 0);
    }

    private int calculateNumberOfReports(Employee employee) {
        if (employee.getDirectReports() == null) {
            return 0;
        }
        int numberOfReports = employee.getDirectReports().size();
        for (Employee report : employee.getDirectReports()) {
            Employee fullReport = employeeRepository.findById(report.getEmployeeId()).orElse(null);
            if (fullReport != null) {
                numberOfReports += calculateNumberOfReports(fullReport);
            }
        }
        return numberOfReports;
    }

    private void logEmployeeDetails(Employee employee, int level) {
        if (employee == null) {
            return;
        }
        String indent = " ".repeat(level * 4);
        LOG.info("{}Employee ID: {}, Name: {} {}", indent, employee.getEmployeeId(), employee.getFirstName(), employee.getLastName());
        if (employee.getDirectReports() != null) {
            for (Employee directReport : employee.getDirectReports()) {
                Employee fullReport = employeeRepository.findById(directReport.getEmployeeId()).orElse(null);
                logEmployeeDetails(fullReport, level + 1);
            }
        }
    }
}