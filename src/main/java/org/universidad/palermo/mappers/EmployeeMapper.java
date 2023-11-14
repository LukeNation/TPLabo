package org.universidad.palermo.mappers;

import org.junit.platform.commons.util.StringUtils;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.entities.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper implements Mapper<Employee>{

    public Employee updateEntity(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setLastName(request.getLastName());
        employee.setSalary(request.getSalary());
        employee.setAssigned(false);
        return employee;
    }

    public void updateEntity(Employee employee, UpdateEmployeeRequest request) {
        employee.setName(StringUtils.isBlank(request.getName())?employee.getName():request.getName());
        employee.setLastName(StringUtils.isBlank(request.getLastName())?employee.getLastName():request.getLastName());
        employee.setSalary(request.getSalary() ==null?employee.getSalary():request.getSalary());
    }

    public EmployeeResponse toResponse(Employee employee){

        EmployeeResponse response = null;
        if(employee != null ) {
            response = new EmployeeResponse();
            response.setEmployeeNumber(employee.getEmployeeNumber());
            response.setName(employee.getFullName());
            response.setSalary(employee.getSalary());
            response.setAssigned(employee.getAssigned());
        }
        return response;
    }

    public List<EmployeeResponse> toResponseList(List<Employee> employeeList) {
        return employeeList.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public Employee toEntity(ResultSet rs) throws SQLException {
        Employee employee = null;
        if(rs.next()) {
            employee = new Employee();
            employee.setEmployeeNumber(rs.getLong(1));
            employee.setName(rs.getString(2));
            employee.setLastName(rs.getString(3));
            employee.setSalary(rs.getDouble(4));
            employee.setAssigned(rs.getBoolean(5));
        }
        return employee;
    }

    public List<Employee> toEntityList(ResultSet rs) throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        while (rs.next()){
            Employee employee = new Employee();
            employee.setEmployeeNumber(rs.getLong(1));
            employee.setName(rs.getString(2));
            employee.setLastName(rs.getString(3));
            employee.setSalary(rs.getDouble(4));
            employee.setAssigned(rs.getBoolean(5));
            employeeList.add(employee);
        }
        return  employeeList;
    }
}
