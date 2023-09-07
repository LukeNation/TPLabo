package org.universidad.palermo.service.interfaces;

import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;

public interface TaskService {
    void CreateTask(CreateTaskRequest req);

    void UpdateTask(UpdateTaskRequest req);

    void DeleteTask(Long taskNumber);

    void GetTask(Long taskNumber);

    void GetAllTasks();

    void GetAllTasksByEmployee(Long employeeId);
}
