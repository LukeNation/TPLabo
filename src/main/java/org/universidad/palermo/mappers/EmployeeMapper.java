package org.universidad.palermo.mappers;

import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.entities.Employee;

public class EmployeeMapper {

    public static Employee toEntity(Long employeeNumber,CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setEmployeeNumber(employeeNumber);
        employee.setName(request.getName());
        employee.setLastName(request.getLastName());
        employee.setSalary(request.getSalary());
        employee.setAssigned(false);
        return employee;
    }

    public static Employee toEntity(Employee employee, UpdateEmployeeRequest request) {
        employee.setName(request.getName());
        employee.setLastName(request.getLastName());
        employee.setSalary(request.getSalary());
        return employee;
    }
}
