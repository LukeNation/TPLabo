package org.universidad.palermo.service.interfaces;

import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse create(CreateEmployeeRequest request);

    EmployeeResponse update(UpdateEmployeeRequest request);

    void delete(Long nroEmpleado);

    EmployeeResponse get(Long nroEmpleado);

    Employee getRaw(Long nroEmpleado);

    List<EmployeeResponse> getAll();

    List<EmployeeResponse> getAll(boolean assigned);

    Long getEmployeeCount();

    Boolean existsEmployee(Long nroEmpleado);

    void assignEmployee(Long employeeNumber);

    void unAssignEmployee(Long employeeNumber);
}

