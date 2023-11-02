package org.universidad.palermo.dao;

import org.universidad.palermo.dao.interfaces.DaoBase;
import org.universidad.palermo.dao.interfaces.IEmployeeDao;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.mappers.EmployeeMapper;

import java.sql.*;
import java.util.List;

public class EmployeeDao implements IEmployeeDao {

    private PreparedStatement startConection(String query) throws Exception {
        Connection cn;
        PreparedStatement ps = null;
        try {
            cn = DaoBase.getConnection();
            ps = cn.prepareStatement(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ps != null){
            return ps;
        }else{
            throw new Exception("Error al crear la sentencia");
        }
    }

    private void endConnection(PreparedStatement ps){
        if(ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Long count() {
        PreparedStatement ps = null;
        Long counter = null;
        try {
            ps = startConection("SELECT COUNT(*) FROM employee");
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                counter = rs.getLong(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return counter;
    }

    @Override
    public Boolean exists(Long idEntity) {
        PreparedStatement ps = null;
        boolean result = false;
        try{
            ps = startConection("SELECT * FROM employee WHERE employee_number = ?");
            ps.setLong(1, idEntity);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return result;
    }

    @Override
    public List<Employee> findAll(boolean assigned) {
        PreparedStatement ps = null;
        List<Employee> employeeList = null;
        try {
            ps = startConection("SELECT * FROM employee WHERE assigned = ?");
            ps.setBoolean(1,assigned);
            ResultSet rs = ps.executeQuery();
            employeeList = EmployeeMapper.toEntityList(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return employeeList;
    }

    @Override
    public Employee findById(Long idEntity) {
        PreparedStatement ps = null;
        Employee employee = null;
        try {
            ps = startConection("SELECT * FROM employee WHERE employee_number = ?");
            ps.setLong(1,idEntity);
            ResultSet rs = ps.executeQuery();
            employee = EmployeeMapper.toEntity(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        PreparedStatement ps = null;
        List<Employee> employeeList = null;
        try {
            ps = startConection("SELECT * FROM employee");
            ResultSet rs = ps.executeQuery();
            employeeList = EmployeeMapper.toEntityList(rs);
        }catch (Exception e) {
           e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return employeeList;
    }

    @Override
    public Employee save(Employee employee) {
        PreparedStatement ps = null;
        try{
            ps = startConection("INSERT INTO employee VALUES(?,?,?,?)");
            ps.setString(1, employee.getName());
            ps.setString(2,employee.getLastName());
            ps.setDouble(3,employee.getSalary());
            ps.setBoolean(4,employee.getAssigned());
            int i = ps.executeUpdate();
            System.out.println("Registros insertados: " + i);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            endConnection(ps);
        }
        return employee;
    }

    @Override
    public Boolean delete(Long nroEmpleado) {
        PreparedStatement ps = null;
        boolean result = false;
        try {
            ps = startConection("DELETE FROM employee WHERE employee_number = ?");
            ps.setLong(1,nroEmpleado);
            int i = ps.executeUpdate();
            System.out.println("Registros eliminados: " + i);
            result = true;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return result;
    }
}
