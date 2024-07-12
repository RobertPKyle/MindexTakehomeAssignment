package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    // Create a new compensation
    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);

        return compensationService.create(compensation);
    }
    
    // gets the compensation of an employee by employeeId
    @GetMapping("/compensation/{employeeId}")
    public Compensation read(@PathVariable String employeeId) {
        LOG.debug("Received compensation read request for employeeId [{}]", employeeId);

        return compensationService.read(employeeId);
    }

    // Takes in an employeeId and Compensation and updates the employee with the new compensation
    @PutMapping("/compensation/{employeeId}")
    public Compensation update(@PathVariable String employeeId, @RequestBody Compensation compensation) {
        LOG.debug("Received compensation update request for employeeId [{}] and compensation [{}]", employeeId, compensation);

        return compensationService.update(employeeId, compensation);
    }
}