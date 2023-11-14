package org.universidad.palermo.service;

import org.universidad.palermo.dao.interfaces.IEmployeeDao;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.mappers.EmployeeMapper;
import org.universidad.palermo.service.interfaces.EmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final IEmployeeDao employeeDao;

    public EmployeeServiceImpl(IEmployeeDao employeeDao, EmployeeMapper employeeMapper) {
        this.employeeDao = employeeDao;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeResponse create(CreateEmployeeRequest request) {
        Employee employee = employeeMapper.updateEntity(request);
        return employeeMapper.toResponse(employeeDao.save(employee));
    }

    @Override
    public EmployeeResponse update(UpdateEmployeeRequest request) {
        Employee employee = employeeDao.findById(request.getEmployeeNumber());
        if(employee != null) {
            employeeMapper.updateEntity(employee, request);
            return employeeMapper.toResponse(employeeDao.update(employee));
        }
        return null;
    }

    @Override
    public void delete(Long nroEmpleado) {
       employeeDao.delete(nroEmpleado);
    }

    @Override
    public EmployeeResponse get(Long nroEmpleado) {
        return employeeMapper.toResponse(getRaw(nroEmpleado));
    }

    @Override
    public Employee getRaw(Long nroEmpleado) {
       return employeeDao.findById(nroEmpleado);
    }

    @Override
    public List<EmployeeResponse> getAll() {
       return employeeDao.findAll().stream().map(employeeMapper::toResponse).toList();
    }

    @Override
    public List<EmployeeResponse> getAll(boolean assigned) {
      return employeeDao.findAll(assigned).stream().map(employeeMapper::toResponse).toList();
    }

    @Override
    public Long getEmployeeCount() {
          return employeeDao.count();
    }

    @Override
    public Boolean existsEmployee(Long nroEmpleado) {
        return employeeDao.exists(nroEmpleado);
    }

    @Override
    public void assignEmployee(Long employeeNumber) {
        Employee employee = employeeDao.findById(employeeNumber);
        if(employee != null){
            employee.setAssigned(true);
            employeeDao.update(employee);
        }
    }

    @Override
    public void unAssignEmployee(Long employeeNumber) {
        Employee employee = employeeDao.findById(employeeNumber);
        if(employee != null){
            employee.setAssigned(false);
            employeeDao.update(employee);
        }
    }

}
