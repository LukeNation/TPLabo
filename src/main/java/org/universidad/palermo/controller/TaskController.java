package org.universidad.palermo.controller;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.service.interfaces.TaskService;

import java.util.List;

@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    public TaskResponse CreateTask(CreateTaskRequest req) {
        return taskService.CreateTask(req);
    }

    public TaskResponse UpdateTask(UpdateTaskRequest req) {
        return taskService.UpdateTask(req);
    }

    public void DeleteTask(Long taskNumber) {
        taskService.DeleteTask(taskNumber);
    }

    public TaskResponse GetTask(Long taskNumber) {
        return taskService.GetTask(taskNumber);
    }

    public List<TaskResponse> GetAllTasks() {
        return taskService.GetAllTasks();
    }

    public List<TaskResponse> GetAllTasksByEmployee(Long employeeId) {
        return taskService.GetAllTasksByEmployee(employeeId);
    }

}
