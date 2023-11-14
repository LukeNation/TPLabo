package org.universidad.palermo.dao.interfaces;

import org.universidad.palermo.entities.Employee;

import java.util.List;

public interface IEmployeeDao extends DaoBase<Employee, Long>{
    List<Employee> findAll(boolean assigned);


}
