package org.universidad.palermo.controller;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.service.interfaces.TaskService;

@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    public void CreateTask(CreateTaskRequest req) {
        taskService.CreateTask(req);
    }

    public void UpdateTask(UpdateTaskRequest req) {
        taskService.UpdateTask(req);
    }

    public void DeleteTask(Long taskNumber) {
        taskService.DeleteTask(taskNumber);
    }

    public void GetTask(Long taskNumber) {
        taskService.GetTask(taskNumber);
    }

    public void GetAllTasks() {
        taskService.GetAllTasks();
    }

    public void GetAllTasksByEmployee(Long employeeId) {
        taskService.GetAllTasksByEmployee(employeeId);
    }

}
