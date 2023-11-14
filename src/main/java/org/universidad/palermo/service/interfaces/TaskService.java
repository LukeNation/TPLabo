package org.universidad.palermo.service.interfaces;

import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.dto.response.TaskStatusResponse;
import org.universidad.palermo.enums.TaskStatusEnum;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(CreateTaskRequest req);

    TaskResponse UpdateTask(UpdateTaskRequest req);

    void deleteTask(Long taskNumber);

    TaskResponse GetTask(Long taskNumber);

    List<TaskResponse> GetAllTasks();

    List<TaskResponse> GetAllTasksByEmployee(Long employeeId);

    Long getTaskCount();

    Boolean existsTask(Long taskNumber);

    TaskResponse assignTask(Long taskNumber, Long employeeNumber);

    List<TaskResponse> getTaskByProject(Long projectNumber);

    TaskStatusResponse updateTaskStatus(Long taskNumber, TaskStatusEnum status, String message);

    List<TaskStatusResponse> getHistoric(Long taskNumber);
}
