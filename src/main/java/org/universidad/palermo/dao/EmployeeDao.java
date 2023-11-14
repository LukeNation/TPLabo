package org.universidad.palermo.dao;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dao.interfaces.DaoBase;
import org.universidad.palermo.dao.interfaces.IEmployeeDao;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.mappers.EmployeeMapper;

import java.sql.*;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeDao implements IEmployeeDao {

    private final EmployeeMapper employeeMapper;

    @Override
    public Long count() {
        return DaoBase.count("employee");
    }

    @Override
    public Boolean exists(Long idEntity) {
        return DaoBase.exists(idEntity,"employee","employee_number");
    }

    @Override
    public List<Employee> findAll(boolean assigned) {
        PreparedStatement ps = null;
        List<Employee> employeeList = null;
        try {
            ps = DaoBase.startConection("SELECT * FROM employee WHERE assigned = ?");
            ps.setBoolean(1,assigned);
            ResultSet rs = ps.executeQuery();
            employeeList = employeeMapper.toEntityList(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DaoBase.endConnection(ps);
        }
        return employeeList;
    }

    @Override
    public Employee findById(Long idEntity) {
        Employee employee = null;
        try {
            employee = (Employee) DaoBase.findById(idEntity,"employee","employee_number",employeeMapper);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employeeList = null;
        try {
            employeeList = (List<Employee>) DaoBase.findAll("employee",employeeMapper);
        }catch (Exception e) {
           e.printStackTrace();
        }
        return employeeList;
    }

    @Override
    public Employee save(Employee employee) {
        PreparedStatement ps = null;
        try{
            ps = DaoBase.startConection("INSERT INTO employee(name,lastname,salary, assigned) VALUES(?,?,?,?)");
            ps.setString(1, employee.getName());
            ps.setString(2,employee.getLastName());
            ps.setDouble(3,employee.getSalary());
            ps.setBoolean(4,employee.getAssigned());
            int i = ps.executeUpdate();
            if(i > 0) {
                Long id = DaoBase.getLastId("employee", "employee_number");
                employee.setEmployeeNumber(id);
            }
            System.out.println("Registros insertados: " + i);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoBase.endConnection(ps);
        }
        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        PreparedStatement ps = null;
        try{
            ps = DaoBase.startConection("UPDATE employee SET name = ?, lastname = ?, salary = ?, assigned = ? WHERE employee_number = ?");
            ps.setString(1, employee.getName());
            ps.setString(2,employee.getLastName());
            ps.setDouble(3,employee.getSalary());
            ps.setBoolean(4,employee.getAssigned());
            ps.setLong(5,employee.getEmployeeNumber());
            int i = ps.executeUpdate();
            System.out.println("Registros actualizados: " + i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoBase.endConnection(ps);
        }
        return employee;
    }

    @Override
    public Boolean delete(Long nroEmpleado) {
        return DaoBase.delete(nroEmpleado,"employee","employee_number");
    }
}
