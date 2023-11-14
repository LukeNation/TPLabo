package org.universidad.palermo.dao.interfaces;

import org.universidad.palermo.entities.Task;
import org.universidad.palermo.entities.TaskStatus;

import java.util.List;

public interface ITaskDao extends DaoBase<Task,Long>{

    List<Task> findAllByEmployee(Long employeeId);

    List<Task> findAllByProject(Long projectNumber);

}
