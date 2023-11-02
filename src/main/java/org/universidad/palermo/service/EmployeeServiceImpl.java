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

    private Long employeeNumber;
    private final IEmployeeDao employeeDao;

    public EmployeeServiceImpl(IEmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
        employeeNumber = employeeDao.count();
    }

    @Override
    public EmployeeResponse create(CreateEmployeeRequest request) {
        Employee employee = EmployeeMapper.updateEntity(++employeeNumber,request);
        return EmployeeMapper.toResponse(employeeDao.save(employee));
    }

    @Override
    public EmployeeResponse update(UpdateEmployeeRequest request) {
        Employee employee = employeeDao.findById(request.getEmployeeNumber());
        if(employee != null) {
            EmployeeMapper.updateEntity(employee, request);
            return EmployeeMapper.toResponse(employeeDao.save(employee));
        }
        return null;
    }

    @Override
    public void delete(Long nroEmpleado) {
       employeeDao.delete(nroEmpleado);
    }

    @Override
    public EmployeeResponse get(Long nroEmpleado) {
        return EmployeeMapper.toResponse(getRaw(nroEmpleado));
    }

    @Override
    public Employee getRaw(Long nroEmpleado) {
       return employeeDao.findById(nroEmpleado);
    }

    @Override
    public List<EmployeeResponse> getAll() {
       return employeeDao.findAll().stream().map(EmployeeMapper::toResponse).toList();
    }

    @Override
    public List<EmployeeResponse> getAll(boolean assigned) {
      return employeeDao.findAll(assigned).stream().map(EmployeeMapper::toResponse).toList();
    }

    @Override
    public Long getEmployeeCount() {
          return employeeDao.count();
    }

    @Override
    public Boolean existsEmployee(Long nroEmpleado) {
        return employeeDao.exists(nroEmpleado);
    }

}
