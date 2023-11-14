package org.universidad.palermo.dao.interfaces;

import org.universidad.palermo.entities.Project;

import java.util.List;

public interface IProjectDao extends DaoBase<Project, Long>{
    Integer countEmployeesInProject(Long idProject);

    boolean includeEmployee(Long projectNumber, Long employeeNumber);

    boolean removeEmployee(Long projectNumber, Long employeeNumber);

    List<Long> findEmployeeList(Long idProject);
}
