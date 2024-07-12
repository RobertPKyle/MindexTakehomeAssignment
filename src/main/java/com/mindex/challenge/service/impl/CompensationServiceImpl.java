package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        // Save the given compensation entity to the repository and return the saved entity
        return compensationRepository.save(compensation);
    }

    @Override
    public Compensation read(String employeeId) {
        // Find the employee with the given employeeId from the repository
        // If the employee is not found, throw a RuntimeException
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Invalid employeeId: " + employeeId));
        
        // Find and return the compensation associated with the found employee
        return compensationRepository.findByEmployee(employee);
    }

    @Override
    public Compensation update(String employeeId, Compensation compensation) {
        // Find the employee with the given employeeId from the repository
        // If the employee is not found, throw a RuntimeException
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Invalid employeeId: " + employeeId));
        
        // Set the found employee to the compensation entity
        compensation.setEmployee(employee);
        
        // Save the updated compensation entity to the repository and return the saved entity
        return compensationRepository.save(compensation);
    }
}