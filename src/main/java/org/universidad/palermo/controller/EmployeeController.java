package org.universidad.palermo.controller;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.service.interfaces.EmployeeService;

import java.util.List;

@RequiredArgsConstructor
public class EmployeeController implements Controller {

    private final EmployeeService employeeService;

    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        return employeeService.create(request);
    }

    public EmployeeResponse updateEmployee(UpdateEmployeeRequest request) {
        return employeeService.update(request);
    }

    public void delete(Long nroEmpleado) {
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
