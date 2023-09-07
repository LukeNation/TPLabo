package org.universidad.palermo.service;

import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.mappers.EmployeeMapper;
import org.universidad.palermo.service.interfaces.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {

    private static Long employeeNumber = 0L;
    private static final Map<Long, Employee> employees = new HashMap<>();
    @Override
    public Employee create(CreateEmployeeRequest request) {
        Employee employee = EmployeeMapper.toEntity(++employeeNumber,request);
        employees.put(employeeNumber, employee);
        return employee;
    }

    @Override
    public Employee update(UpdateEmployeeRequest request) {
        Employee employee = employees.get(request.getEmployeeNumber());
        if(employee != null){
            EmployeeMapper.toEntity(employee, request);
        }
        return employee;
    }

    @Override
    public void delete(Long nroEmpleado) {
        employees.remove(nroEmpleado);
    }

    @Override
    public Employee get(Long nroEmpleado) {
        return employees.get(nroEmpleado);
    }

    @Override
    public List<Employee> getAll() {
        return employees.values().stream().toList();
    }

    @Override
    public List<Employee> getAll(boolean assigned) {
        return employees.values().stream().filter(employee -> employee.getAssigned()==assigned).toList();
    }
}
