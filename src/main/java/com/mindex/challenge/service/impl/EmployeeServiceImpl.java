package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        // Save the given employee entity to the repository and return the saved entity
        return employeeRepository.save(employee);
    }

    @Override
    public Employee read(String id) {
        // Find the employee with the given id from the repository
        // If the employee is not found, throw a RuntimeException
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid employeeId: " + id));
    }

    @Override
    public Employee update(Employee employee) {
        // Save the given employee entity to the repository and return the updated entity
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> listAll() {
        // Retrieve all employee entities from the repository and return them as a list
        return employeeRepository.findAll();
    }
}
