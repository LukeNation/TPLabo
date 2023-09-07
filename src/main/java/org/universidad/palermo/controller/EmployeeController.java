package org.universidad.palermo.controller;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.service.interfaces.EmployeeService;

@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    public void createEmployee(CreateEmployeeRequest request) {
        System.out.println(employeeService.create(request));
    }

    public void updateEmployee(UpdateEmployeeRequest request) {
        System.out.println(employeeService.update(request));
    }

    public void deleteEmployee(Long nroEmpleado) {
        employeeService.delete(nroEmpleado);
    }

    public void getEmployee(Long nroEmpleado) {
        System.out.println(employeeService.get(nroEmpleado));
    }

    public void getAllEmployees() {
        employeeService.getAll().forEach(System.out::println);
    }

    public void getFreeEmployees() {
        employeeService.getAll(false).forEach(System.out::println);
    }

    public void getAssignedEmployees() {
        employeeService.getAll(true).forEach(System.out::println);
    }
}
