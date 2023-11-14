package org.universidad.palermo.controller;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.dto.response.TaskStatusResponse;
import org.universidad.palermo.enums.TaskStatusEnum;
import org.universidad.palermo.service.interfaces.TaskService;

import java.util.List;

@RequiredArgsConstructor
public class TaskController implements Controller {

    private final TaskService taskService;

    public TaskResponse CreateTask(CreateTaskRequest req) {
        return taskService.createTask(req);
    }

    public TaskResponse UpdateTask(UpdateTaskRequest req) {
        return taskService.UpdateTask(req);
    }

    public void delete(Long taskNumber) {
        taskService.deleteTask(taskNumber);
    }

    public TaskResponse getTask(Long taskNumber) {
        return taskService.GetTask(taskNumber);
    }

    public List<TaskResponse> getAllTasks() {
        return taskService.GetAllTasks();
    }

    public List<TaskResponse> GetAllTasksByEmployee(Long employeeId) {
        return taskService.GetAllTasksByEmployee(employeeId);
    }

    public TaskStatusResponse updateTaskStatus(Long taskNumber, String message, TaskStatusEnum status) {
        return taskService.updateTaskStatus(taskNumber, status, message);
    }

    public List<TaskStatusResponse> getHistoric(Long taskNumber) {
        return taskService.getHistoric(taskNumber);
    }

}
