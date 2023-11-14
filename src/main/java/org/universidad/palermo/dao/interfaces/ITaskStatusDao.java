package org.universidad.palermo.dao.interfaces;

import org.universidad.palermo.entities.TaskStatus;

import java.util.List;

public interface ITaskStatusDao extends DaoBase<TaskStatus,Long> {

    List<TaskStatus> getHistoric(Long taskNumber);
}
