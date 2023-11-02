package org.universidad.palermo.dao.interfaces;

import org.universidad.palermo.entities.Project;

public interface IProjectDao extends DaoBase<Project, Long>{
    Integer countEmployeesInProject(Long idProject);
}
