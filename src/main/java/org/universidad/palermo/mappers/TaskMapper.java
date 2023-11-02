package org.universidad.palermo.mappers;

import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.entities.Task;
import org.universidad.palermo.enums.ProjectStatus;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static Task toEntity(Long taskNumber, CreateTaskRequest req) {
        Task task = new Task();
        task.setTaskNumber(taskNumber);
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setEstimatedHours(req.getEstimatedHours());
        task.setStatus(ProjectStatus.PENDING.getStatus());
        task.setWorkedHours(0D);
        return task;
    }

    public static void updateEntity(Task task, UpdateTaskRequest req) {
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setEstimatedHours(req.getEstimatedHours());
        task.setWorkedHours(req.getWorkedHours());
    }

    public static TaskResponse toResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setTaskNumber(task.getTaskNumber());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setEstimatedHours(task.getEstimatedHours());
        response.setWorkedHours(task.getWorkedHours());
        response.setAssignedEmployee(EmployeeMapper.toResponse(task.getAssignedEmployee()));
        return response;
    }

    public static List<TaskResponse> toResponseList(List<Task> taskList) {
        return taskList.stream().map(TaskMapper::toResponse).collect(Collectors.toList());
    }
}
