package org.universidad.palermo.service.interfaces;

import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    Employee create(CreateEmployeeRequest request);

    Employee update(UpdateEmployeeRequest request);

    void delete(Long nroEmpleado);

    Employee get(Long nroEmpleado);

    List<Employee> getAll();

    List<Employee> getAll(boolean assigned);

}
