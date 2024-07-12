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
        return employeeRepository.save(employee);
    }

    @Override
    public Employee read(String id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid employeeId: " + id));
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @Override
    public List<Employee> listAll() {
        return employeeRepository.findAll();
    }
}
