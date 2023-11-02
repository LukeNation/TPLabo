package org.universidad.palermo.controller;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.service.interfaces.EmployeeService;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        return employeeService.create(request);
    }

    public EmployeeResponse updateEmployee(UpdateEmployeeRequest request) {
        return employeeService.update(request);
    }

    public void deleteEmployee(Long nroEmpleado) {
        employeeService.delete(nroEmpleado);
    }

    public EmployeeResponse getEmployee(Long nroEmpleado) {
        return employeeService.get(nroEmpleado);
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAll();
    }

    public List<EmployeeResponse>  getFreeEmployees() {
        return employeeService.getAll(false);
    }

    public List<EmployeeResponse>  getAssignedEmployees() {
        return employeeService.getAll(true);
    }
}
