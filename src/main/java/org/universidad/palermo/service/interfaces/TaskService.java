package org.universidad.palermo.service.interfaces;

import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.entities.Task;

import java.util.List;

public interface TaskService {
    TaskResponse CreateTask(CreateTaskRequest req);

    TaskResponse UpdateTask(UpdateTaskRequest req);

    void DeleteTask(Long taskNumber);

    TaskResponse GetTask(Long taskNumber);

    List<TaskResponse> GetAllTasks();

    List<TaskResponse> GetAllTasksByEmployee(Long employeeId);

    int getTaskCount();

    Boolean existsTask(Long taskNumber);

    TaskResponse assignTask(Long taskNumber, Long employeeNumber);
}
